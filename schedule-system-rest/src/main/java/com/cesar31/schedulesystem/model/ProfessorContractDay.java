package com.cesar31.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "professor_contract_day")
public class ProfessorContractDay {

    @Id
    @SequenceGenerator(name = "professorContractDayIdGenerator", sequenceName = "SEQ_PROFESSOR_CONTRACT_DAY", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professorContractDayIdGenerator")
    @Column(name = "professor_contract_day_id")
    private Long professorContractDayId;

    /**
     * Bidirectional relationship with {@link Professor}
     */
    @JsonBackReference(value = "professor")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professor")
    private Professor professor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cat_day")
    private Category catDay;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public Long getProfessorContractDayId() {
        return professorContractDayId;
    }

    public void setProfessorContractDayId(Long professorContractDayId) {
        this.professorContractDayId = professorContractDayId;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Category getCatDay() {
        return catDay;
    }

    public void setCatDay(Category catDay) {
        this.catDay = catDay;
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
