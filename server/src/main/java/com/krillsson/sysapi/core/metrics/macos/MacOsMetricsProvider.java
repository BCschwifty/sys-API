package com.krillsson.sysapi.core.metrics.macos;

import com.krillsson.sysapi.core.TickManager;
import com.krillsson.sysapi.core.metrics.defaultimpl.DefaultMetricsFactory;
import com.krillsson.sysapi.core.speed.SpeedMeasurementManager;
import com.krillsson.sysapi.util.Utils;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class MacOsMetricsProvider extends DefaultMetricsFactory {

    private final HardwareAbstractionLayer hal;
    private final OperatingSystem operatingSystem;
    private final SpeedMeasurementManager speedMeasurementManager;

    public MacOsMetricsProvider(HardwareAbstractionLayer hal, OperatingSystem operatingSystem, SpeedMeasurementManager speedMeasurementManager, TickManager tickManager, Utils utils) {
        super(hal, operatingSystem, speedMeasurementManager, tickManager, utils);
        this.hal = hal;
        this.operatingSystem = operatingSystem;
        this.speedMeasurementManager = speedMeasurementManager;
    }

    @Override
    public boolean initialize() {
        super.initialize();
        // this is a not so clean solution since super.initialize will first set DefaultCpuMetrics
        // and then this directly overwrites that variable
        setDriveMetrics(new MacOsDriveProvider(operatingSystem, hal, speedMeasurementManager));
        return true;
    }
}
