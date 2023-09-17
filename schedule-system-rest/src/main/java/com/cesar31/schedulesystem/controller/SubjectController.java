package com.cesar31.schedulesystem.controller;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.model.Subject;
import com.cesar31.schedulesystem.repository.SubjectRepository;

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

@Path("subjects")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubjectController {

    @Inject
    SubjectRepository repository;

    @GET
    public Response findAll() {
        var subjects = repository.findAll();
        subjects.forEach(Subject::clean);
        return Response.ok(subjects).build();
    }

    @GET
    @Path("{subjectId}")
    public Response findById(@PathParam("subjectId") Long subjectId) {
        var subject = repository.findBy(subjectId);
        if (subject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        subject.clean();
        return Response.ok(subject).build();
    }

    @POST
    public Response create(@Valid Subject subject) {
        repository.save(subject);
        return Response.ok().build();
    }

    @PUT
    @Path("{subjectId}")
    public Response update(@PathParam("subjectId") Long subjectId, @Valid Subject subject) throws ScheduleSysException {
        repository.update(subjectId, subject);
        return Response.ok().build();
    }
}
