package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.model.ProfessorSubject;
import com.cesar31.schedulesystem.model.QProfessor;
import com.cesar31.schedulesystem.model.QProfessorSubject;
import com.cesar31.schedulesystem.model.QSubject;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;
import java.util.List;

@RequestScoped
@Repository(forEntity = ProfessorSubject.class)
public abstract class ProfessorSubjectRepository extends AbstractEntityRepository<ProfessorSubject, Long> {

    public List<ProfessorSubject> findAll(
            Long professorId, Long subjectId, Double qualification,
            Integer yearsOfExperience, Double professorAverageQualification,
            Integer subjNumberOfCredits, Boolean subjRequired, Double subjIndex
    ) {
        var qf = new JPAQueryFactory(this.entityManager());
        var _professorSubject = new QProfessorSubject("_professor_subject");
        var _subject = new QSubject("_subject");
        var _professor = new QProfessor("_professor");

        var query = qf
                .selectFrom(_professorSubject)
                .innerJoin(_professorSubject.professor, _professor)
                .innerJoin(_professorSubject.subject, _subject);

        if (professorId != null)
            query.where(_professor.professorId.eq(professorId));

        if (subjectId != null)
            query.where(_subject.subjectId.eq(subjectId));

        if (qualification != null)
            query.where(_professorSubject.qualification.eq(qualification));

        if (yearsOfExperience != null)
            query.where(_professorSubject.yearsOfExperience.eq(yearsOfExperience));

        if (professorAverageQualification != null)
            query.where(_professor.averageQualification.eq(professorAverageQualification));

        if (subjNumberOfCredits != null)
            query.where(_subject.numberOfCredits.eq(subjNumberOfCredits));

        if (subjRequired != null)
            query.where(_subject.required.eq(subjRequired));

        if (subjIndex != null)
            query.where(_subject.subjectIndex.eq(subjIndex));

        return query.fetch();
    }
}
