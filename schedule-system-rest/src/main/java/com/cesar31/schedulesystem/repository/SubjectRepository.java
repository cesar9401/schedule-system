package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.model.Subject;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

@RequestScoped
@Repository(forEntity = Subject.class)
public abstract class SubjectRepository extends AbstractEntityRepository<Subject, Long> {

    public Subject update(Long subjectId, Subject subject) throws ScheduleSysException {
        var entity = this.findBy(subjectId);
        if (entity == null)
            throw new ScheduleSysException("subject_not_found")
                    .status(Response.Status.NOT_FOUND);

        entity.merge(subject);
        return this.save(entity);
    }
}
