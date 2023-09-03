package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.model.AcademicCycle;
import com.cesar31.schedulesystem.model.QAcademicCycle;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;

@RequestScoped
@Repository(forEntity = AcademicCycle.class)
public abstract class AcademicCycleRepository extends AbstractEntityRepository<AcademicCycle, Long> {

    public AcademicCycle findByIdOrLast(Long academicCycleId) {
        if (academicCycleId != null)
            return this.findBy(academicCycleId);

        var qf = new JPAQueryFactory(this.entityManager());
        var _academicCycle = new QAcademicCycle("_academic_cycle");

        return qf.selectFrom(_academicCycle)
                .orderBy(_academicCycle.academicCycleId.desc())
                .fetchFirst();
    }
}
