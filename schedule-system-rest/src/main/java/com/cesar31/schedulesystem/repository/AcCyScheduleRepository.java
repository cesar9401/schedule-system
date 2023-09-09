package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.model.AcCySchedSubj;
import com.cesar31.schedulesystem.model.AcCySchedule;
import com.cesar31.schedulesystem.model.AcCySubject;
import com.cesar31.schedulesystem.model.AcademicCycle;
import com.cesar31.schedulesystem.model.Category;
import com.cesar31.schedulesystem.model.Classroom;
import com.cesar31.schedulesystem.model.Professor;
import com.cesar31.schedulesystem.model.ProfessorContractDay;
import com.cesar31.schedulesystem.model.ProfessorSubject;
import com.cesar31.schedulesystem.model.QAcCySchedule;
import com.cesar31.schedulesystem.util.enums.CategoryEnum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestScoped
@Repository(forEntity = AcCySchedule.class)
public abstract class AcCyScheduleRepository extends AbstractEntityRepository<AcCySchedule, Long> {

    @Inject
    AcademicCycleRepository acCyRepository;

    @Inject
    AcCySubjectRepository acCySubjectRepository;

    @Inject
    ClassroomRepository classroomRepository;

    @Inject
    ProfessorSubjectRepository professorSubjectRepository;

    @Inject
    CategoryRepository categoryRepository;

    @Inject
    Logger logger;

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

    public void createScheduleByAcCyId(Long academicCycleId) throws ScheduleSysException {
        var acCy = this.acCyRepository.findByIdOrLast(academicCycleId);
        if (acCy == null)
            throw new ScheduleSysException("academic_cycle_not_found")
                    .status(Response.Status.NOT_FOUND);

        logger.info("Academic cycle: {}", acCy.getAcademicCycleId());

        var classrooms = classroomRepository.findAll();
        var acCySubjects = acCySubjectRepository.findByAcademicCycle_academicCycleId(acCy.getAcademicCycleId());
        var professorSubjects = professorSubjectRepository.findAll();

        var monday = categoryRepository.findByInternalId(CategoryEnum.DW_MONDAY.internalId);

        // TODO: create schedules here
        var availableClassrooms = this.getAvailableClassrooms(classrooms, new ArrayList<>(), monday, LocalDateTime.of(2000, 1, 1, 8, 0), LocalDateTime.of(2000, 1, 1, 0, 0));
        availableClassrooms.forEach(c -> {
            logger.info("{} -> {}", c.getClassroomId(), c.getName());
        });
    }

    private Boolean createSchedule(List<AcCySchedSubj> schedule, List<AcCySubject> subjects, List<Classroom> classrooms, List<ProfessorSubject> professorSubjects) {
        var availableSubjects = this.getAvailableAcCySubjects(schedule, subjects);
        // TODO: set subject
        for (var acCySubject : availableSubjects) {

        }

        // TODO: set classroom
        // TODO: set professor

        // TODO: next step, if fails,

        return false;
    }

    private AcCySchedSubj nextFree(AcademicCycle acCy, List<AcCySchedSubj> acCySchedSubjs) {
        var classDays = acCy.getClassDays();
        for (var classDay : classDays) {
            var currentClassDay = acCySchedSubjs
                    .stream()
                    .filter(c -> c.getCatDay().is(classDay.getCatDay()))
                    .collect(Collectors.toList());

            if (currentClassDay.isEmpty()) {
                // return first time
                var acCySchedSubj = new AcCySchedSubj();
                acCySchedSubj.setCatDay(classDay.getCatDay());
                acCySchedSubj.setStartTime(classDay.getStartTime());
                acCySchedSubj.setEndTime(classDay.getStartTime().plusHours(1L));

                return acCySchedSubj;
            }

            var nextFree = this.nextFree(acCySchedSubjs, classDay.getCatDay(), toLocalTime(classDay.getStartTime()), toLocalTime(classDay.getEndTime()));
            if (nextFree != null) {
                return nextFree;
            }
        }

        return null;
    }

    private AcCySchedSubj nextFree(List<AcCySchedSubj> acCySchedSubjs, Category catDay, LocalTime startT, LocalTime endT) {
        if (startT.equals(endT)) return null;

        var freePeriod = acCySchedSubjs
                .stream()
                .filter(sched -> toLocalTime(sched.getStartTime()).equals(startT) && toLocalTime(sched.getEndTime()).equals(endT))
                .findFirst();

        if (freePeriod.isEmpty()) {
            // create schedule
            var acCySchedSubj = new AcCySchedSubj();
            acCySchedSubj.setCatDay(catDay);
            acCySchedSubj.setStartTime(LocalDateTime.of(2000, 1, 1, startT.getHour(), startT.getMinute()));
            acCySchedSubj.setEndTime(LocalDateTime.of(2000, 1, 1, startT.getHour() + 1, startT.getMinute()));
            return acCySchedSubj;
        }

        return nextFree(acCySchedSubjs, catDay, startT.plusHours(1L), endT);
    }

    /**
     * Check if all schedule is complete
     *
     * @param acCy           current academic cycle
     * @param acCySchedSubjs current schedule or periods of classes
     * @return true if the schedule is complete
     */
    private Boolean accept(AcademicCycle acCy, List<AcCySchedSubj> acCySchedSubjs) {
        var classDays = acCy.getClassDays();
        for (var classDay : classDays) {
            var currentClassDay = acCySchedSubjs
                    .stream()
                    .filter(c -> c.getCatDay().is(classDay.getCatDay()))
                    .collect(Collectors.toList());

            var startT = toLocalTime(classDay.getStartTime());
            var endT = toLocalTime(classDay.getEndTime());

            var isPresent = isPeriodPresent(currentClassDay, startT, endT);
            if (!isPresent) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check is a period is present
     *
     * @param schedule
     * @param startT
     * @param endT
     * @return
     */
    private Boolean isPeriodPresent(List<AcCySchedSubj> schedule, LocalTime startT, LocalTime endT) {
        if (startT.equals(endT)) return true;

        var isCurPresent = schedule
                .stream()
                .anyMatch(sched -> toLocalTime(sched.getStartTime()).equals(startT) && startT.plusHours(1L).equals(toLocalTime(sched.getEndTime())));

        return isCurPresent && isPeriodPresent(schedule, startT.plusHours(1L), endT);
    }

    /**
     * LocalDateTime to LocalTime
     *
     * @param dateTime the date time
     * @return the time
     */
    private LocalTime toLocalTime(LocalDateTime dateTime) {
        return LocalTime.of(dateTime.getHour(), dateTime.getMinute());
    }

    private Boolean reject(List<Classroom> classrooms, List<ProfessorSubject> professorSubjects) {
        return false;
    }

    /**
     * @param acCySubjects subjects for the present ac cy
     * @param schedSubjs   schedule
     * @return all available subjects, not present in the schedule
     */
    private List<AcCySubject> getAvailableAcCySubjects(List<AcCySchedSubj> schedSubjs, List<AcCySubject> acCySubjects) {
        var subjMap = schedSubjs
                .stream()
                .collect(Collectors.groupingBy(sched -> sched.getAcCySubject().getAcCySubjectId(), Collectors.counting()));

        // List of subjects of the ac cy that have less than the minimum number of periods required
        return acCySubjects
                .stream()
                .filter(acCySubj -> subjMap.getOrDefault(acCySubj.getAcCySubjectId(), Long.MIN_VALUE) <= acCySubj.getNumberOfPeriods())
                .collect(Collectors.toList());
    }

    /**
     * Get a list of classrooms at specific period
     *
     * @param classrooms list of all classrooms
     * @param schedSubjs list of schedules
     * @param day        day of the period
     * @param startTime  star time of the period
     * @param endTime    end time of the period
     * @return list of available classrooms at any periods
     */
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
     * Check if given two days, two start times and two end times, the period they represent are the same
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
                && toLocalTime(start0).equals(toLocalTime(start1))
                && toLocalTime(end0).equals(toLocalTime(end1));
    }

    private Boolean isBetween(LocalDateTime start0, LocalDateTime end0, LocalDateTime start1, LocalDateTime end1) {
        var _start0 = toLocalTime(start0);
        var _end0 = toLocalTime(end0);
        var _start1 = toLocalTime(start1);
        var _end1 = toLocalTime(end1);
        return (_start0.isBefore(_start1) || _start0.equals(_start1)) && (_end0.isAfter(_end1) || _end0.equals(_end1));
    }
}
