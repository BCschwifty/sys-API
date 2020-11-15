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
package com.krillsson.sysapi.rest

import com.krillsson.sysapi.auth.BasicAuthorizer
import com.krillsson.sysapi.config.UserConfiguration
import com.krillsson.sysapi.core.domain.motherboard.MotherboardMapper
import com.krillsson.sysapi.core.domain.sensors.SensorsInfoMapper
import com.krillsson.sysapi.core.metrics.MotherboardMetrics
import com.krillsson.sysapi.dto.motherboard.Motherboard
import com.krillsson.sysapi.dto.sensors.HealthData
import io.dropwizard.auth.Auth
import javax.annotation.security.RolesAllowed
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("motherboard")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed(BasicAuthorizer.AUTHENTICATED_ROLE)
class MotherboardResource(var provider: MotherboardMetrics) {
    @GET
    @RolesAllowed(BasicAuthorizer.AUTHENTICATED_ROLE)
    fun getRoot(): Motherboard {
        return MotherboardMapper.INSTANCE.map(provider.motherboard())
    }

    @GET
    @Path("health")
    fun getHealths(): List<HealthData> {
        return SensorsInfoMapper.INSTANCE.mapDatas(provider.motherboardHealth())
    }
}