/*
 * Sys-Api (https://github.com/Krillsson/sys-api)
 *
 * Copyright 2017 Christian Jensen / Krillsson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Maintainers:
 * contact[at]christian-jensen[dot]se
 */

package com.krillsson.sysapi.resources;

import com.krillsson.sysapi.core.DefaultInfoProvider;
import com.krillsson.sysapi.core.InfoProvider;
import com.krillsson.sysapi.core.domain.system.System;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;

import javax.ws.rs.core.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link SystemResource}.
 */
@RunWith(MockitoJUnitRunner.class)
public class SystemResourceTest {

    private static final InfoProvider provider = new DefaultInfoProvider();
    private static final OperatingSystem os = mock(OperatingSystem.class);
    private static final CentralProcessor processor = mock(CentralProcessor.class);
    private static final GlobalMemory memory = mock(GlobalMemory.class);
    private static final PowerSource[] powerSources = new PowerSource[]{mock(PowerSource.class)};
    private static final Sensors sensors = mock(Sensors.class);

    @ClassRule
    public static final ResourceTestRule RESOURCES = ResourceTestRule.builder()
            .addResource(new SystemResource(provider, os, processor, memory, powerSources, sensors))
            .build();

    @Before
    public void setUp() {
        when(sensors.getCpuTemperature()).thenReturn((double) 30);
        when(os.getProcessCount()).thenReturn(70);
        when(os.getThreadCount()).thenReturn(100);
    }

    @Test
    public void getSystem() throws Exception {
        double temperature = 30;
        double voltage = 12;
        int processCount = 70;
        int threadCount = 100;

        when(sensors.getCpuTemperature()).thenReturn(temperature);
        when(sensors.getCpuVoltage()).thenReturn(temperature);
        when(os.getProcessCount()).thenReturn(processCount);
        when(os.getThreadCount()).thenReturn(threadCount);
        final System response = RESOURCES.getJerseyTest().target("/system")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .get(System.class);
        assertThat(response.getCpu().getCpuHealth().getTemperatures()[0]).isEqualTo(temperature);
        assertThat(response.getCpu().getCpuHealth().getVoltage()).isEqualTo(voltage);
        assertThat(response.getOperatingSystem().getProcessCount()).isEqualTo(processCount);
        assertThat(response.getOperatingSystem().getThreadCount()).isEqualTo(threadCount);
    }

    @After
    public void tearDown() {
        reset(os);
        reset(processor);
        reset(memory);
        reset(powerSources);
        reset(sensors);
    }

}