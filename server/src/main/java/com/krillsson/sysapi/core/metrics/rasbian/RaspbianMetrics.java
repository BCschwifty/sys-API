package com.krillsson.sysapi.core.metrics.rasbian;

import com.krillsson.sysapi.core.speed.SpeedMeasurementManager;
import com.krillsson.sysapi.util.Ticker;
import com.krillsson.sysapi.core.metrics.defaultimpl.DefaultMetrics;
import com.krillsson.sysapi.util.Utils;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class RaspbianMetrics extends DefaultMetrics {

    private final HardwareAbstractionLayer hal;
    private final OperatingSystem operatingSystem;
    private final Ticker ticker;
    private final Utils utils;

    public RaspbianMetrics(HardwareAbstractionLayer hal, OperatingSystem operatingSystem, SpeedMeasurementManager speedMeasurementManager, Ticker ticker, Utils utils) {
        super(hal, operatingSystem, speedMeasurementManager, ticker, utils);
        this.hal = hal;
        this.operatingSystem = operatingSystem;
        this.ticker = ticker;
        this.utils = utils;
    }


    @Override
    public void initialize() {
        super.initialize();
        // this is a not so clean solution since super.initialize will first set DefaultCpuMetrics
        // and then this directly overwrites that variable
        setCpuMetrics(new RaspbianCpuMetrics(hal, operatingSystem, ticker, utils));
    }
}