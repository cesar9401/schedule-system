package com.cesar31.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "subject_schedule")
public class SubjectSchedule {

    @Id
    @SequenceGenerator(name = "subjectScheduleIdGenerator", sequenceName = "SEQ_SUBJECT_SCHEDULE", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectScheduleIdGenerator")
    @Column(name = "subject_schedule_id")
    private Long subjectScheduleId;

    /**
     * Bidirectional relationship with {@link AcCySubject}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ac_cy_subject_id")
    @JsonBackReference(value = "acCySubject0")
    private AcCySubject acCySubject;

    /**
     * Bidirectional relationship with {@link Professor}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    /**
     * Unidirectional relationship with {@link Classroom}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public Long getSubjectScheduleId() {
        return subjectScheduleId;
    }

    public void setSubjectScheduleId(Long subjectScheduleId) {
        this.subjectScheduleId = subjectScheduleId;
    }

    public AcCySubject getAcCySubject() {
        return acCySubject;
    }

    public void setAcCySubject(AcCySubject acCySubject) {
        this.acCySubject = acCySubject;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
