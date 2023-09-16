package com.cesar31.schedulesystem.dto;

import com.cesar31.schedulesystem.model.AcCySchedSubj;
import com.cesar31.schedulesystem.model.AcCySchedule;
import com.cesar31.schedulesystem.model.AcCySubject;
import com.cesar31.schedulesystem.model.Professor;
import com.cesar31.schedulesystem.model.ProfessorSubject;
import com.cesar31.schedulesystem.model.Subject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AcCyScheduleAuxDto {

    private AcCySchedule schedule;
    private List<AcCySchedSubj> availableSchedSubj;
    private List<AcCySubject> subjects;
    private Map<Subject, Set<Professor>> subjectProfessorMap;
    private Map<Professor, Set<PeriodDto>> professorPeriodMap;

    private final List<AcCyScheduleAuxDto> children;

    private AcCyScheduleAuxDto() {
        this.children = new ArrayList<>();
    }

    public AcCyScheduleAuxDto(AcCySchedule schedule, List<AcCySubject> subjects, List<ProfessorSubject> professorSubjects) {
        this();
        this.schedule = schedule;

        this.availableSchedSubj = schedule
                .getAcCySchedSubjs()
                .stream()
                .filter(this::isAvailable)
                .collect(Collectors.toList());

        this.subjects = subjects
                .stream()
                .flatMap(acCySubject -> IntStream
                        .range(0, acCySubject.getNumberOfPeriods())
                        .mapToObj(i -> acCySubject)
                )
                .collect(Collectors.toList());

        this.subjectProfessorMap = professorSubjects
                .stream()
                .collect(Collectors.groupingBy(ProfessorSubject::getSubject, Collectors.mapping(ProfessorSubject::getProfessor, Collectors.toSet())));

        professorPeriodMap = professorSubjects
                .stream()
                .map(ProfessorSubject::getProfessor)
                .distinct()
                .map(this::toPeriods)
                .reduce(new HashMap<>(), (tmpMap, item) -> {
                    tmpMap.putAll(item);
                    return tmpMap;
                });
    }

    public AcCyScheduleAuxDto(AcCyScheduleAuxDto other) {
        this();
        this.schedule = new AcCySchedule(other.schedule);
        this.availableSchedSubj = this.schedule
                .getAcCySchedSubjs()
                .stream()
                .filter(this::isAvailable)
                .collect(Collectors.toList());
        this.subjects = new ArrayList<>(other.subjects);
        this.subjectProfessorMap = new HashMap<>(other.subjectProfessorMap);
        this.professorPeriodMap = new HashMap<>(other.professorPeriodMap);
    }

    private Boolean isAvailable(AcCySchedSubj schedSubj) {
        return schedSubj.getProfessor() == null && schedSubj.getAcCySubject() == null;
    }

    private HashMap<Professor, Set<PeriodDto>> toPeriods(Professor professor) {
        var periods = professor
                .getContractDays()
                .stream()
                .flatMap(contract -> {
                    var startH = contract.getStartTime().getHour();
                    var endH = contract.getEndTime().getHour();
                    return IntStream.range(0, endH - startH)
                            .mapToObj(i -> new PeriodDto(contract.getCatDay(), contract.getStartTime().plusHours(i), contract.getStartTime().plusHours(i + 1)));
                })
                .collect(Collectors.toSet());

        return new HashMap<>(Map.of(professor, periods));
    }

    public AcCySchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(AcCySchedule schedule) {
        this.schedule = schedule;
    }

    public List<AcCySchedSubj> getAvailableSchedSubj() {
        return availableSchedSubj;
    }

    public void setAvailableSchedSubj(List<AcCySchedSubj> availableSchedSubj) {
        this.availableSchedSubj = availableSchedSubj;
    }

    public List<AcCySubject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<AcCySubject> subjects) {
        this.subjects = subjects;
    }

    public Map<Subject, Set<Professor>> getSubjectProfessorMap() {
        return subjectProfessorMap;
    }

    public void setSubjectProfessorMap(Map<Subject, Set<Professor>> subjectProfessorMap) {
        this.subjectProfessorMap = subjectProfessorMap;
    }

    public Map<Professor, Set<PeriodDto>> getProfessorPeriodMap() {
        return professorPeriodMap;
    }

    public void setProfessorPeriodMap(Map<Professor, Set<PeriodDto>> professorPeriodMap) {
        this.professorPeriodMap = professorPeriodMap;
    }

    public List<AcCyScheduleAuxDto> getChildren() {
        return children;
    }
}
