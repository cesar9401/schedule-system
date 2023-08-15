package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.model.Classroom;
import com.cesar31.schedulesystem.model.QClassroom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import java.util.Optional;

@Repository(forEntity = Classroom.class)
public abstract class ClassroomRepository extends AbstractEntityRepository<Classroom, Long> {

    public Optional<Classroom> findById(Long classroomId) {
        var _classroom = new QClassroom("_classroom");
        var queryFactory = new JPAQueryFactory(this.entityManager());
        return queryFactory
                .selectFrom(_classroom)
                .where(_classroom.classroomId.eq(classroomId))
                .stream()
                .findFirst();
    }
}
