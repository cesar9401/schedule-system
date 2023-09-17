package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.repository.CategoryRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryController {

    @Inject
    CategoryRepository repository;

    @GET
    @Path("by-parent-internal-id/{internalId}")
    public Response findByParentInternalId(@PathParam("internalId") Long parentInternalId) {
        return Response.ok(repository.findByParentInternalId(parentInternalId)).build();
    }
}
