package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.model.AcCyScheduleModel;
import com.cesar31.schedulesystem.repository.AcCyScheduleModelRepository;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("ac-cy-schedule-models")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AcCyScheduleModelController {

    @Inject
    AcCyScheduleModelRepository repository;

    @GET
    @Path("{academicCycleId}")
    public Response findByAcademicCycle(@PathParam("academicCycleId") Long academicCycleId) {
        var models = repository.findByAcademicCycle_academicCycleId(academicCycleId);
        models.forEach(AcCyScheduleModel::clean);
        return Response.ok(models).build();
    }

    @POST
    @Path("{academicCycleId}")
    public Response startModel(@PathParam("academicCycleId") Long academicCycleId, @Valid AcCyScheduleModel scheduleModel) throws ScheduleSysException {
        repository.startModel(academicCycleId, scheduleModel);
        return Response.ok().build();
    }
}
