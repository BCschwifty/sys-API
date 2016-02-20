package com.krillsson.sysapi.domain.cpu;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Cpu {
    private double systemUsagePercent;
    private double voltage;
    private double temperature;
    private double fanRpm;
    private double fanPercent;
    private CpuInfo cpuInfo;
    private CpuLoad totalCpuLoad;
    private List<CpuLoad> cpuLoadPerCore;

    public Cpu(CpuInfo cpuInfo, double systemUsagePercent, double voltage, double temperature, double fanRpm, double fanPercent, CpuLoad totalCpuLoad, List<CpuLoad> cpuLoadPerCore) {
        this.cpuInfo = cpuInfo;
        this.systemUsagePercent = systemUsagePercent;
        this.voltage = voltage;
        this.temperature = temperature;
        this.fanRpm = fanRpm;
        this.fanPercent = fanPercent;
        this.totalCpuLoad = totalCpuLoad;
        this.cpuLoadPerCore = cpuLoadPerCore;
    }

    public Cpu(CpuInfo cpuInfo, double v, CpuLoad cpuLoad, List<CpuLoad> cpuLoads)
    {
        this.cpuInfo = cpuInfo;
        this.systemUsagePercent = v;
        this.totalCpuLoad = cpuLoad;
        this.cpuLoadPerCore = cpuLoads;
    }

    @JsonProperty
    public double getVoltage()
    {
        return voltage;
    }

    @JsonProperty
    public double getTemperature()
    {
        return temperature;
    }

    @JsonProperty
    public double getFanRpm()
    {
        return fanRpm;
    }

    @JsonProperty
    public double getFanPercent()
    {
        return fanPercent;
    }

    @JsonProperty
    public CpuInfo getCpuInfo() {
        return cpuInfo;
    }

    @JsonProperty
    public double getSystemUsagePercent() {
        return systemUsagePercent;
    }

    @JsonProperty
    public CpuLoad getTotalCpuLoad() {
        return totalCpuLoad;
    }

    @JsonProperty
    public List<CpuLoad> getCpuLoadPerCore() {
        return cpuLoadPerCore;
    }

    public void setVoltage(double voltage)
    {
        this.voltage = voltage;
    }

    public void setTemperature(double temperature)
    {
        this.temperature = temperature;
    }

    public void setFanRpm(double fanRpm)
    {
        this.fanRpm = fanRpm;
    }

    public void setFanPercent(double fanPercent)
    {
        this.fanPercent = fanPercent;
    }
}
