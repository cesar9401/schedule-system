package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.model.AcCySubject;
import com.cesar31.schedulesystem.model.QAcCySubject;
import com.cesar31.schedulesystem.model.QSubject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
@Repository(forEntity = AcCySubject.class)
public abstract class AcCySubjectRepository extends AbstractEntityRepository<AcCySubject, Long> {

    public List<AcCySubject> findAll(
            Long academicCycleId, Long subjectId, String section,
            Integer priority, Integer numberOfCredits, Boolean required,
            Double subjectIndex
    ) {
        var qf = new JPAQueryFactory(this.entityManager());
        var _acCySubject = new QAcCySubject("_ac_cy_subject");
        var _subject = new QSubject("_subject");

        var query = qf
                .selectFrom(_acCySubject)
                .innerJoin(_acCySubject.subject, _subject);

        if (academicCycleId != null)
            query.where(_acCySubject.academicCycle.academicCycleId.eq(academicCycleId));

        if (subjectId != null)
            query.where(_subject.subjectId.eq(subjectId));

        if (section != null && !section.isEmpty())
            query.where(_acCySubject.sectionCode.likeIgnoreCase("%" + section + "%"));

        if (priority != null)
            query.where(_acCySubject.priority.eq(priority));

        if (numberOfCredits != null)
            query.where(_subject.numberOfCredits.eq(numberOfCredits));

        if (required != null)
            query.where(_subject.required.eq(required));

        if (subjectIndex != null)
            query.where(_subject.subjectIndex.eq(subjectIndex));

        return query.fetch();
    }
}
