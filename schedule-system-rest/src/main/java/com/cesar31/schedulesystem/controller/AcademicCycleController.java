package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.model.AcademicCycle;
import com.cesar31.schedulesystem.repository.AcademicCycleRepository;

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

@Path("academic-cycles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AcademicCycleController {

    @Inject
    AcademicCycleRepository repository;

    @GET
    public Response findAll() {
        return Response.ok(repository.findAll()).build();
    }

    @GET
    @Path("{academicCycleId}")
    public Response findById(@PathParam("academicCycleId") Long academicCycleId) {
        var academicCycle = repository.findBy(academicCycleId);
        return academicCycle != null
                ? Response.ok(academicCycle).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response create(@Valid AcademicCycle academicCycle) {
        repository.save(academicCycle);
        return Response.ok().build();
    }
}
