package com.krillsson.sysapi.core.monitoring.monitors

import com.krillsson.sysapi.core.domain.monitor.MonitorConfig
import com.krillsson.sysapi.core.domain.monitor.MonitoredValue
import com.krillsson.sysapi.core.monitoring.Monitor
import com.krillsson.sysapi.core.monitoring.MonitorMetricQueryEvent
import java.util.*

class CpuTemperatureMonitor(override val id: UUID, override val config: MonitorConfig<MonitoredValue.NumericalValue>) : Monitor<MonitoredValue.NumericalValue>() {

    override val type: Type = Type.CPU_TEMP

    override fun selectValue(event: MonitorMetricQueryEvent): MonitoredValue.NumericalValue =
        MonitoredValue.NumericalValue(event.load().cpuLoad.cpuHealth.temperatures.firstOrNull()?.toLong() ?: 0)

    override fun isPastThreshold(value: MonitoredValue.NumericalValue): Boolean {
        return value > config.threshold
    }
}