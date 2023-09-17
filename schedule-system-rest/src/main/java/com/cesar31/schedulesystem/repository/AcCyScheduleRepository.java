package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.dto.AcCySchedSubjDto;
import com.cesar31.schedulesystem.dto.AcCyScheduleAuxDto;
import com.cesar31.schedulesystem.dto.PeriodDto;
import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.export.DataColumn;
import com.cesar31.schedulesystem.export.DataType;
import com.cesar31.schedulesystem.export.ExcelFileExporter;
import com.cesar31.schedulesystem.model.AcCySchedSubj;
import com.cesar31.schedulesystem.model.AcCySchedule;
import com.cesar31.schedulesystem.model.Professor;
import com.cesar31.schedulesystem.model.QAcCySchedule;
import com.cesar31.schedulesystem.util.enums.CategoryEnum;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.apache.xmlbeans.ThreadLocalUtil;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public byte[] exportById(Long acCyScheduleId) throws ScheduleSysException {
        var acCySchedule = this.findBy(acCyScheduleId);
        if (acCySchedule == null)
            throw new ScheduleSysException("schedule_not_found")
                    .status(Response.Status.NOT_FOUND);

        var schedSubjects = acCySchedule.getAcCySchedSubjs();

        var catDays = categoryRepository.findByParentInternalId(CategoryEnum.DAY_OF_WEEK.internalId);

        // periods grouping by day
        var periods = schedSubjects
                .stream()
                .map(sched -> new PeriodDto(sched.getCatDay(), sched.getStartTime(), sched.getEndTime()))
                .collect(Collectors.groupingBy(PeriodDto::getCatDay, Collectors.toSet()));

        periods.forEach((day, period) -> {
            logger.info("day: {}, period: {}", day.getDescription(), period);
        });

        // group by classroom and periods
        var schedByClassrooms = schedSubjects
                .stream()
                .map(AcCySchedSubjDto::new)
                .collect(Collectors.groupingBy(AcCySchedSubjDto::getClassroom, Collectors.groupingBy(AcCySchedSubjDto::getPeriod, Collectors.collectingAndThen(Collectors.toList(), list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException("repeated_period_for_classroom");
                    }
                    return list.get(0);
                }))));

        // get a list of the hours (just start and end time)
        var periodsInOneDay = new ArrayList<>(periods.
                values()
                .stream()
                .findFirst()
                .orElse(Set.of()))
                .stream()
                .sorted()
                .collect(Collectors.toList());

        var fileData = new LinkedHashMap<DataColumn, List<?>>();

        // times
        var periodsStr = periodsInOneDay
                .stream()
                .map(PeriodDto::getTimeStr)
                .collect(Collectors.toList());
        fileData.put(new DataColumn("Schedule/Classroom-Day", DataType.STRING), periodsStr);

        // schedule for every classroom
        for (var classroom : schedByClassrooms.keySet()) {
            logger.info("Looking periods for classroom: {}", classroom.getName());
            var periodsInClass = schedByClassrooms.get(classroom);

            // for day
            for (var day : catDays) {
                logger.info("looking for day: {}", day.getDescription());
                if (!periods.containsKey(day)) continue;
                var periodsClassDayStr = periods.get(day)
                        .stream()
                        .sorted()
                        .map(periodsInClass::get)
                        .map(AcCySchedSubjDto::schedSubjNoClassroomStr)
                        .collect(Collectors.toList());

                if (!periodsClassDayStr.isEmpty()) {
                    fileData.put(new DataColumn(classroom.getName().concat(" - ").concat(day.getDescription()), DataType.STRING), periodsClassDayStr);
                }
            }
        }

        try {
            var xlsxFile = ExcelFileExporter
                    .aExcelFileExporter(fileData)
                    .export();

            ThreadLocalUtil.clearAllThreadLocals();
            return xlsxFile.toByteArray();
        } catch (IOException e) {
            throw new ScheduleSysException("error_exporting_to_xlsx");
        }
    }

    public AcCySchedule createEmptySchedule(Long academicCycleId) throws ScheduleSysException {
        var acCy = acCyRepository.findByIdOrLast(academicCycleId);
        if (acCy == null)
            throw new ScheduleSysException("academic_cycle_not_found")
                    .status(Response.Status.NOT_FOUND);

        var classrooms = classroomRepository.findAll();
        var schedule = new AcCySchedule();
        schedule.setAcademicCycle(acCy);

        // create schedule details
        var classDays = acCy.getClassDays();
        var acCySchedSubjs = classDays
                .stream()
                .flatMap(classDay -> {
                    var startT = classDay.getStartTime().getHour();
                    var endT = classDay.getEndTime().getHour();
                    return classrooms
                            .stream()
                            .flatMap(classroom -> IntStream.range(0, endT - startT)
                                    .mapToObj(i -> {
                                        var acCySchedSubj = new AcCySchedSubj();
                                        acCySchedSubj.setClassroom(classroom);
                                        acCySchedSubj.setCatDay(classDay.getCatDay());
                                        acCySchedSubj.setStartTime(classDay.getStartTime().plusHours(i));
                                        acCySchedSubj.setEndTime(acCySchedSubj.getStartTime().plusHours(1));
                                        return acCySchedSubj;
                                    }));
                })
                .collect(Collectors.toList());
        schedule.setAcCySchedSubjs(acCySchedSubjs);
        return schedule;
    }

    public void createScheduleByAcCyId(Long academicCycleId) throws ScheduleSysException {
        var acCy = acCyRepository.findByIdOrLast(academicCycleId);

        if (acCy == null)
            throw new ScheduleSysException("academic_cycle_not_found")
                    .status(Response.Status.NOT_FOUND);
        var acCySubjects = acCySubjectRepository.findByAcademicCycle_academicCycleId(acCy.getAcademicCycleId());
        var professorSubjects = professorSubjectRepository.findAll();
        var schedule = this.createEmptySchedule(academicCycleId);

        var scheduleAux = new AcCyScheduleAuxDto(schedule, acCySubjects, professorSubjects);
        var schedulesAUx = this.createSchedule(scheduleAux);
        schedulesAUx
                .stream()
                .map(sched -> {
                    var acCySchedule = sched.getSchedule();
                    acCySchedule.getAcCySchedSubjs().forEach(schedSubj -> schedSubj.setAcCySchedule(acCySchedule));
                    return acCySchedule;
                })
                .forEach(this::save);

        logger.info("Result: {} schedules save successfully", schedulesAUx.size());
    }

    private List<AcCyScheduleAuxDto> createSchedule(AcCyScheduleAuxDto scheduleAux) {
        var schedules = new ArrayList<AcCyScheduleAuxDto>();
        var scheduleSubjects = scheduleAux.getAvailableSchedSubj();
        var subjects = scheduleAux.getSubjects();

        for (var scheduleSubj : scheduleSubjects) {
            for (var subject : subjects) {
                var subjectProfessorSet = scheduleAux.getSubjectProfessorMap().getOrDefault(subject.getSubject(), Set.of());

                if (subjectProfessorSet.isEmpty()) {
                    // logger.warn("No professor for subject: {}", subject.getSubject().getName());
                    continue;
                }

                for (Professor professor : subjectProfessorSet) {
                    var periodsSet = scheduleAux.getProfessorPeriodMap().getOrDefault(professor, Set.of());

                    var curPeriod = new PeriodDto(scheduleSubj.getCatDay(), scheduleSubj.getStartTime(), scheduleSubj.getEndTime());
                    if (!periodsSet.contains(curPeriod)) {
                        // logger.warn("no period for professor: {}", professor.getEmail());
                        continue;
                    }

                    scheduleSubj.setProfessor(professor);
                    scheduleSubj.setAcCySubject(subject);

                    // build new aux
                    var newAux = new AcCyScheduleAuxDto(scheduleAux);
                    // remove subject from available
                    newAux.getSubjects().remove(subject);

                    // remove professor period
                    newAux.getProfessorPeriodMap().getOrDefault(professor, Set.of()).remove(curPeriod);

                    // remove professor, if the professor don't have any period
                    if (newAux.getProfessorPeriodMap().getOrDefault(professor, Set.of()).isEmpty()) {
                        newAux.getProfessorPeriodMap().remove(professor);
                    }

                    scheduleSubj.setProfessor(null);
                    scheduleSubj.setAcCySubject(null);
                    scheduleAux.getChildren().add(newAux);
                    var schedules1 = createSchedule(newAux);
                    schedules.addAll(schedules1);
                }
            }
        }

        if (scheduleAux.getChildren().isEmpty()) {
            schedules.add(scheduleAux);
        }
        return schedules;
    }
}
