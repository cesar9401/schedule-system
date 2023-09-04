package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.model.AcCySchedSubj;
import com.cesar31.schedulesystem.model.AcCySchedule;
import com.cesar31.schedulesystem.model.AcCySubject;
import com.cesar31.schedulesystem.model.Category;
import com.cesar31.schedulesystem.model.Classroom;
import com.cesar31.schedulesystem.model.Professor;
import com.cesar31.schedulesystem.model.ProfessorContractDay;
import com.cesar31.schedulesystem.model.ProfessorSubject;
import com.cesar31.schedulesystem.model.QAcCySchedule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<Classroom> getAvailableClassrooms(List<Classroom> classrooms, List<AcCySchedSubj> schedSubjs, Category day, LocalDateTime startTime, LocalDateTime endTime) {
        var busyClassrooms = schedSubjs
                .stream()
                .filter(sched -> !isSamePeriod(sched.getCatDay(), sched.getStartTime(), sched.getEndTime(), day, startTime, endTime))
                .mapToLong(sched -> sched.getClassroom().getClassroomId())
                .distinct()
                .boxed()
                .collect(Collectors.toList());

        return classrooms
                .stream()
                .filter(c -> !busyClassrooms.contains(c.getClassroomId()))
                .collect(Collectors.toList());
    }

    private List<Professor> getAvailableProfessors(List<ProfessorSubject> professorSubj, List<AcCySchedSubj> schedSubjs, AcCySubject subject, Category day, LocalDateTime startTime, LocalDateTime endTime) {
        // teachers who teach the course and are contracted for the day and time requested
        var availableProfessors = professorSubj
                .stream()
                .filter(prof -> prof.getSubject().getSubjectId().equals(subject.getSubject().getSubjectId()))// give the course
                .flatMap(prof -> prof.getProfessor().getContractDays().stream())
                .filter(cd -> cd.getCatDay().is(day))
                .filter(cd -> isBetween(cd.getStartTime(), cd.getEndTime(), startTime, endTime))
                .map(ProfessorContractDay::getProfessor)
                .distinct()
                .collect(Collectors.toList());

        // all professors who have a class for the day and time requested
        var busyProfessors = schedSubjs
                .stream()
                .filter(sched -> isSamePeriod(sched.getCatDay(), sched.getStartTime(), sched.getEndTime(), day, startTime, endTime))// non-free period
                .mapToLong(sched -> sched.getProfessor().getProfessorId())
                .boxed()
                .collect(Collectors.toList());

        return availableProfessors
                .stream()
                .filter(professor -> !busyProfessors.contains(professor.getProfessorId()))
                .collect(Collectors.toList());
    }

    /**
     * Check if given two days, two start times and two end time, the period they represent are the same
     *
     * @param day0
     * @param start0
     * @param end0
     * @param day1
     * @param start1
     * @param end1
     * @return
     */
    private Boolean isSamePeriod(
            Category day0, LocalDateTime start0, LocalDateTime end0,
            Category day1, LocalDateTime start1, LocalDateTime end1
    ) {
        return day0.is(day1)
                && start0.getHour() == start1.getHour()
                && start0.getMinute() == start1.getMinute()
                && end0.getHour() == end1.getHour()
                && end0.getMinute() == end1.getMinute();
    }

    private Boolean isBetween(LocalDateTime start0, LocalDateTime end0, LocalDateTime start1, LocalDateTime end1) {
        var _start0 = LocalDateTime.of(2000, 1, 1, start0.getHour(), start0.getMinute());
        var _end0 = LocalDateTime.of(2000, 1, 1, end0.getHour(), end0.getMinute());
        var _start1 = LocalDateTime.of(2000, 1, 1, start1.getHour(), start1.getMinute());
        var _end1 = LocalDateTime.of(2000, 1, 1, end1.getHour(), end1.getMinute());

        return (_start0.isBefore(_start1) || _start0.equals(_start1)) && (_end0.isAfter(_end1) || _end0.isEqual(_end1));
    }
}
