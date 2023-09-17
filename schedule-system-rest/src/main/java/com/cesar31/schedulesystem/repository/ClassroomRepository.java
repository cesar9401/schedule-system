package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.model.Classroom;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;

@RequestScoped
@Repository(forEntity = Classroom.class)
public abstract class ClassroomRepository extends AbstractEntityRepository<Classroom, Long> {
}
