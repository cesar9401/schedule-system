package com.cesar31.schedulesystem.repository;

import com.cesar31.schedulesystem.dto.AcCyScheduleAuxDto;
import com.cesar31.schedulesystem.dto.AcCyScheduleModelDto;
import com.cesar31.schedulesystem.dto.PeriodDto;
import com.cesar31.schedulesystem.exception.ScheduleSysException;
import com.cesar31.schedulesystem.model.AcCySchedSubj;
import com.cesar31.schedulesystem.model.AcCySchedule;
import com.cesar31.schedulesystem.model.AcCyScheduleModel;
import com.cesar31.schedulesystem.model.AcCySubject;
import com.cesar31.schedulesystem.model.Professor;
import com.cesar31.schedulesystem.util.comparator.AcCySubjectComparator;
import com.cesar31.schedulesystem.util.enums.CategoryEnum;
import org.apache.deltaspike.data.api.AbstractEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequestScoped
@Repository(forEntity = AcCyScheduleModel.class)
public abstract class AcCyScheduleModelRepository extends AbstractEntityRepository<AcCyScheduleModel, Long> {

    @Inject
    AcademicCycleRepository acCyRepository;

    @Inject
    AcCySubjectRepository acCySubjectRepository;

    @Inject
    ClassroomRepository classroomRepository;

    @Inject
    ProfessorSubjectRepository professorSubjectRepository;

    @Inject
    Logger logger;

    public abstract List<AcCyScheduleModel> findByAcademicCycle_academicCycleId(Long academicCycleId);

    public List<AcCyScheduleModelDto> findAllDtoByAcademicCycleId(Long academicCycleId) {
        return this.findByAcademicCycle_academicCycleId(academicCycleId)
                .stream()
                .map(AcCyScheduleModelDto::new)
                .collect(Collectors.toList());
    }

    public void startModel(Long academicCycleId, Long subjectOrderIntId, AcCyScheduleModel scheduleModel) throws ScheduleSysException {
        var acCy = acCyRepository.findBy(academicCycleId);
        if (acCy == null)
            throw new ScheduleSysException("academic_cycle_not_found")
                    .status(Response.Status.NOT_FOUND);

        var acCySubjects = acCySubjectRepository.findByAcademicCycle_academicCycleId(acCy.getAcademicCycleId());
        var professorSubjects = professorSubjectRepository.findAll();
        var schedule = this.createEmptySchedule(academicCycleId);

        var scheduleAux = new AcCyScheduleAuxDto(schedule, acCySubjects, professorSubjects);

        // subjects order
        if (subjectOrderIntId != null) {
            var subjectOrder = this.getOrder(subjectOrderIntId);
            if (subjectOrder.isEmpty()) {
                throw new ScheduleSysException("invalid_subject_order");
            }

            logger.info("Order subject by: {}", subjectOrder.get().name());
            if (CategoryEnum.SO_SHUFFLE.equals(subjectOrder.get())) {
                Collections.shuffle(scheduleAux.getSubjects());
            } else {
                var comparator = new AcCySubjectComparator(subjectOrder.get());
                var orderAcCySubjects = scheduleAux.getSubjects()
                        .stream()
                        .sorted(comparator)
                        .collect(Collectors.toList());
                scheduleAux.setSubjects(orderAcCySubjects);
            }
        }

        var schedulesAUx = this.createSchedule(scheduleAux);

        var schedules = schedulesAUx
                .stream()
                .map(sched -> {
                    var acCySchedule = sched.getSchedule();
                    acCySchedule.getAcCySchedSubjs().forEach(schedSubj -> schedSubj.setAcCySchedule(acCySchedule));
                    acCySchedule.setAcCyScheduleModel(scheduleModel);

                    var totalPeriods = acCy
                            .getAcCySubjects()
                            .stream()
                            .mapToLong(AcCySubject::getNumberOfPeriods)
                            .sum();

                    var totalCovered = acCySchedule
                            .getAcCySchedSubjs()
                            .stream()
                            .filter(schedSubj -> schedSubj.getProfessor() != null && schedSubj.getAcCySubject() != null)
                            .count();

                    acCySchedule.setTotalNumberOfPeriods(totalPeriods);
                    acCySchedule.setTotalCoveredPeriods(totalCovered);
                    return acCySchedule;
                })
                .collect(Collectors.toList());

        scheduleModel.setAcademicCycle(acCy);
        scheduleModel.setAcCySchedules(schedules);
        this.save(scheduleModel);
        logger.info("Model created successfully");
    }

    private AcCySchedule createEmptySchedule(Long academicCycleId) throws ScheduleSysException {
        var acCy = acCyRepository.findByIdOrLast(academicCycleId);
        if (acCy == null)
            throw new ScheduleSysException("academic_cycle_not_found")
                    .status(Response.Status.NOT_FOUND);

        var classrooms = classroomRepository.findAll();
        var schedule = new AcCySchedule();
        // schedule.setAcademicCycle(acCy);

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

                    // remove professor, if the professor doesn't have any period
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

    private Optional<CategoryEnum> getOrder(Long categoryInternalId) {
        return Arrays.stream(CategoryEnum.values())
                .filter(cat -> cat.internalId.equals(categoryInternalId))
                .findFirst();
    }
}
