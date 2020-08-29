package com.krillsson.sysapi.dto.monitor;

public class CreateMonitor {

    private String idToMonitor;
    private long inertiaInSeconds;
    private MonitorType type;
    private Double threshold;

    public CreateMonitor(String idToMonitor, long inertiaInSeconds, MonitorType type, Double threshold) {
        this.idToMonitor = idToMonitor;
        this.inertiaInSeconds = inertiaInSeconds;
        this.type = type;
        this.threshold = threshold;
    }

    public CreateMonitor() {
    }

    public String getIdToMonitor() {
        return idToMonitor;
    }

    public void setIdToMonitor(String id) {
        this.idToMonitor = id;
    }

    public long getInertiaInSeconds() {
        return inertiaInSeconds;
    }

    public void setInertiaInSeconds(long inertiaInSeconds) {
        this.inertiaInSeconds = inertiaInSeconds;
    }

    public MonitorType getType() {
        return type;
    }

    public void setType(MonitorType type) {
        this.type = type;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "CreateMonitor{" +
                "idToMonitor='" + idToMonitor + '\'' +
                ", inertiaInSeconds=" + inertiaInSeconds +
                ", type=" + type +
                ", threshold=" + threshold +
                '}';
    }
}
