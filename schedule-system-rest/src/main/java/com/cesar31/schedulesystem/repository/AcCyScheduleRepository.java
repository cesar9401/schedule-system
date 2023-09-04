package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.model.AcCySchedule;
import com.cesar31.schedulesystem.model.QAcCySchedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.List;

@RequestScoped
@Repository(forEntity = AcCySchedule.class)
public abstract class AcCyScheduleRepository extends AbstractEntityRepository<AcCySchedule, Long> {

    AcademicCycleRepository acCyRepository;
    AcCySubjectRepository acCySubjectRepository;
    ClassroomRepository classroomRepository;
    ProfessorSubjectRepository professorSubjectRepository;

    @Inject
    public AcCyScheduleRepository(
            AcademicCycleRepository acCyRepository,
            AcCySubjectRepository acCySubjectRepository,
            ClassroomRepository classroomRepository,
            ProfessorSubjectRepository professorSubjectRepository
    ) {
        this.acCyRepository = acCyRepository;
        this.acCySubjectRepository = acCySubjectRepository;
        this.classroomRepository = classroomRepository;
        this.professorSubjectRepository = professorSubjectRepository;
    }

    public void createScheduleByAcCyId(Long academicCycleId) throws ScheduleSysException {
        var acCy = this.acCyRepository.findByIdOrLast(academicCycleId);
        if (acCy == null)
            throw new ScheduleSysException("academic_cycle_not_found")
                    .status(Response.Status.NOT_FOUND);

        var classrooms = classroomRepository.findAll();
        var acCySubjects = acCySubjectRepository.findByAcademicCycle_academicCycleId(acCy.getAcademicCycleId());
        var professorSubjects = professorSubjectRepository.findAll();

        // TODO: create schedules here
    }

    public List<AcCySchedule> findAll(Long academicCycleId, Boolean isValid) {
        var qf = new JPAQueryFactory(this.entityManager());
        var _acCySchedule = new QAcCySchedule("_ac_cy_schedule");

        var query = qf.selectFrom(_acCySchedule);

        if (academicCycleId != null)
            query.where(_acCySchedule.academicCycle.academicCycleId.eq(academicCycleId));

        if (isValid != null)
            query.where(_acCySchedule.isValid.eq(isValid));

        return query.fetch();
    }
}
