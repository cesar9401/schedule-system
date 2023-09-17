package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.model.Professor;
import com.cesar31.schedulesystem.repository.ProfessorRepository;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("professors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorController {

    @Inject
    ProfessorRepository repository;

    @GET
    public Response findAll() {
        var professors = repository.findAll();
        professors.forEach(Professor::clean);
        return Response.ok(professors).build();
    }

    @GET
    @Path("{professorId}")
    public Response findById(@PathParam("professorId") Long professorId) {
        var professor = repository.findBy(professorId);
        if (professor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        professor.clean();
        return Response.ok(professor).build();
    }

    @POST
    public Response create(@Valid Professor professor) {
        repository.save(professor);
        return Response.ok().build();
    }

    @PUT
    @Path("{professorId}")
    public Response update(@PathParam("professorId") Long professorId, @Valid Professor professor) throws ScheduleSysException {
        repository.update(professorId, professor);
        return Response.ok().build();
    }
}
