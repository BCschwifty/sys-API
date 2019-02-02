package com.krillsson.sysapi.core.history;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.krillsson.sysapi.config.HistoryConfiguration;
import com.krillsson.sysapi.core.domain.system.SystemLoad;
import io.dropwizard.lifecycle.Managed;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryManager implements Managed {
    private final History<SystemLoad> history;
    private final EventBus eventBus;
    private final HistoryConfiguration configuration;

    public HistoryManager(HistoryConfiguration configuration, EventBus eventBus, History<SystemLoad> history) {
        this.configuration = configuration;
        this.eventBus = eventBus;
        this.history = history;
    }

    public HistoryManager(HistoryConfiguration configuration, EventBus eventBus) {
        this(configuration, eventBus, new History<>());
    }

    @Subscribe
    public void onEvent(HistoryMetricQueryEvent event) {
        history.record(event.load());
        history.purge(configuration.getPurging().getOlderThan(), configuration.getPurging().getUnit());
    }

    @Override
    public void start() {
        eventBus.register(this);
    }

    @Override
    public void stop() {
        eventBus.unregister(this);
    }

    public List<HistoryEntry<SystemLoad>> getHistory() {
        return history.get();
    }

    public List<HistoryEntry<SystemLoad>> getHistoryLimitedToDates(@Nullable ZonedDateTime fromDate, @Nullable ZonedDateTime toDate) {
        if (fromDate == null && toDate == null) {
            return getHistory();
        }
        ZonedDateTime from = fromDate != null ? fromDate : ZonedDateTime.of(LocalDateTime.MIN, ZoneId.systemDefault());
        ZonedDateTime to = toDate != null ? toDate : ZonedDateTime.of(LocalDateTime.MAX, ZoneId.systemDefault());

        return history.get()
                .stream()
                .filter(e -> e.date.isAfter(from) && e.date.isBefore(to))
                .collect(Collectors.toList());
    }
}