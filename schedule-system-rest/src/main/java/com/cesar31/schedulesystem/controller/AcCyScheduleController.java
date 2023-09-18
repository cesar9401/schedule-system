package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.repository.AcCyScheduleRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ac-cy-schedules")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AcCyScheduleController {

    @Inject
    AcCyScheduleRepository acCyScheduleRepository;

    @GET
    @Path("by-schedule-model/{acCyScheduleModelId}")
    public Response findByAcCyScheduleModelId(@PathParam("acCyScheduleModelId") Long acCyScheduleModelId) {
        return Response.ok(acCyScheduleRepository.findAllByAcCyScheduleModelId(acCyScheduleModelId)).build();
    }

    @GET
    @Path("export/{acCyScheduleId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response export(@PathParam("acCyScheduleId") Long acCyScheduleId) throws ScheduleSysException {
        var file = acCyScheduleRepository.exportById(acCyScheduleId);
        return Response
                .ok(file)
                .type("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .build();
    }
}
