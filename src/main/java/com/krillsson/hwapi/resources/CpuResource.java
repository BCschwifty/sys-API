package com.krillsson.hwapi.resources;

import io.dropwizard.auth.Auth;
import com.krillsson.hwapi.representation.config.UserConfiguration;
import com.krillsson.hwapi.representation.cpu.Cpu;
import com.krillsson.hwapi.representation.cpu.CpuTime;
import com.krillsson.hwapi.sigar.CpuMetrics;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("cpu")
@Produces(MediaType.APPLICATION_JSON)
public class CpuResource extends Resource {

    private CpuMetrics cpuMetrics;

    public CpuResource(CpuMetrics cpuMetrics) {
    this.cpuMetrics = cpuMetrics;
    }

    @GET
    @Override
    public Cpu getRoot(@Auth UserConfiguration user) {
        return cpuMetrics.getCpu();
    }

    @Path("{core}")
    @GET
    public CpuTime getCpuTimeByCore(@Auth UserConfiguration user, @PathParam("core") int core) {
        try {
            return cpuMetrics.getCpuTimeByCoreIndex(core);
        } catch (IllegalArgumentException e) {
            throw buildWebException(Response.Status.NOT_FOUND, e.getMessage());
        }
    }
}