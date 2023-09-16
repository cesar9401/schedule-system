package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.repository.AcCyScheduleRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    public Response check() throws ScheduleSysException {
        acCyScheduleRepository.createScheduleByAcCyId1(null);
        return Response.ok().build();
    }
}
