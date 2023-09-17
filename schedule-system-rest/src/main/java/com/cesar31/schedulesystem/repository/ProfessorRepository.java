package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.model.Professor;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

@RequestScoped
@Repository(forEntity = Professor.class)
public abstract class ProfessorRepository extends AbstractEntityRepository<Professor, Long> {

    public void update(Long professorId, Professor professor) throws ScheduleSysException {
        var entity = this.findBy(professorId);
        if (entity == null)
            throw new ScheduleSysException("professor_not_found")
                    .status(Response.Status.NOT_FOUND);

        entity.merge(professor);
        this.save(entity);
    }
}
