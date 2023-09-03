package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.model.Classroom;
import com.cesar31.schedulesystem.model.QClassroom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;
import java.util.List;
import java.util.Optional;

@RequestScoped
@Repository(forEntity = Classroom.class)
public abstract class ClassroomRepository extends AbstractEntityRepository<Classroom, Long> {

    public List<Classroom> findAll(String name, Integer capacity) {
        var qf = new JPAQueryFactory(this.entityManager());
        var _classroom = new QClassroom("_classroom");

        var query = qf.selectFrom(_classroom);

        if (name != null)
            query.where(_classroom.name.likeIgnoreCase("%" + name + "%"));

        if (capacity != null)
            query.where(_classroom.recommendedCapacity.eq(capacity));

        return query.fetch();
    }

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
