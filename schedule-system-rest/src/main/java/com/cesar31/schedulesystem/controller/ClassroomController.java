package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.model.Classroom;
import com.cesar31.schedulesystem.repository.ClassroomRepository;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;


@Path("classrooms")
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
        return repository.findById(classroomId)
                .map(classroom -> Response.ok(classroom).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response create(@Valid Classroom classroom) {
        repository.save(classroom);
        return Response.ok().build();
    }
}
