package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.repository.ClassroomRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("classrooms")
public class ClassroomController {

    @Inject
    ClassroomRepository repository;

    @GET
    public Response findAll() {
        return Response.ok(repository.findAll()).build();
    }
}
