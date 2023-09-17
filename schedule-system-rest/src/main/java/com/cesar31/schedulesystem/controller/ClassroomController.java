package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.model.Classroom;
import com.cesar31.schedulesystem.repository.ClassroomRepository;

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

@Path("classrooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClassroomController {

    @Inject
    ClassroomRepository repository;

    @GET
    public Response findAll() {
        return Response.ok(repository.findAll()).build();
    }

    @GET
    @Path("{classroomId}")
    public Response findById(@PathParam("classroomId") Long classroomId) {
        var classroom = repository.findBy(classroomId);
        return classroom != null
                ? Response.ok(classroom).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response create(@Valid Classroom classroom) {
        repository.save(classroom);
        return Response.ok().build();
    }
}
