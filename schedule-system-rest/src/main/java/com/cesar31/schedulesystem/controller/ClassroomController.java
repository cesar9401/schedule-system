package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.repository.ClassroomRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("classrooms")
public class ClassroomController {

    @Inject
    ClassroomRepository repository;

    @GET
    public Response findAll() {
        return Response.ok(repository.findAll()).build();
    }
}
