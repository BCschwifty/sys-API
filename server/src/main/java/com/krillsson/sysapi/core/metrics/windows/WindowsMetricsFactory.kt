package com.krillsson.sysapi.core.metrics.windows

import com.krillsson.sysapi.core.connectivity.ConnectivityCheckManager
import com.krillsson.sysapi.core.metrics.defaultimpl.*
import com.krillsson.sysapi.core.speed.SpeedMeasurementManager
import com.krillsson.sysapi.util.Ticker
import com.krillsson.sysapi.util.Utils
import com.krillsson.sysapi.util.asOperatingSystem
import com.krillsson.sysapi.util.asPlatform
import oshi.PlatformEnum
import oshi.hardware.HardwareAbstractionLayer
import oshi.software.os.OperatingSystem

object WindowsMetricsFactory {
    fun create(
        os: OperatingSystem,
        hal: HardwareAbstractionLayer,
        platformEnum: PlatformEnum,
        ticker: Ticker,
        utils: Utils,
        measurementManager: SpeedMeasurementManager,
        connectivityCheckManager: ConnectivityCheckManager
    ): WindowsMetrics? {
        val ohmFactory = OHMManagerFactory()
        if (ohmFactory.prerequisitesFilled() && ohmFactory.initialize()) {

            val monitorManager = ohmFactory.monitorManager
            val defaultCpuLoadMetrics = DefaultCpuLoadMetrics(hal.processor, ticker)

            val cpuMetrics =
                WindowsCpuMetrics(hal, os, defaultCpuLoadMetrics, monitorManager, ticker, utils)
            val networkMetrics =
                WindowsNetworkMetrics(ticker, hal, measurementManager, connectivityCheckManager, monitorManager)
            val gpuMetrics = WindowsGpuMetrics(hal, monitorManager)
            val driveMetrics =
                WindowsDriveMetrics(os, hal, measurementManager)
            val diskMetrics = DefaultDiskMetrics(hal, measurementManager)
            val fileSystemMetrics = DefaultFileSystemMetrics(os)
            val processesMetrics = DefaultProcessesMetrics(os, hal, ticker)
            val motherboardMetrics = WindowsMotherboardMetrics(hal, monitorManager)
            val memoryMetrics = DefaultMemoryMetrics(hal, os)
            val systemMetrics = DefaultSystemMetrics(
                cpuMetrics,
                networkMetrics,
                driveMetrics,
                diskMetrics,
                fileSystemMetrics,
                memoryMetrics,
                processesMetrics,
                motherboardMetrics,
                gpuMetrics,
                os.asOperatingSystem(),
                platformEnum.asPlatform()
            )
            return WindowsMetrics(
                cpuMetrics,
                networkMetrics,
                gpuMetrics,
                driveMetrics,
                diskMetrics,
                fileSystemMetrics,
                processesMetrics,
                motherboardMetrics,
                memoryMetrics,
                systemMetrics
            )
        }

        return null
    }
}