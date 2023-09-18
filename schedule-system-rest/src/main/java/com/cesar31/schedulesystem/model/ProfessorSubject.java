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

@Entity
@Table(name = "professor_subject")
public class ProfessorSubject {

    @Id
    @SequenceGenerator(name = "professorSubjectIdGenerator", sequenceName = "SEQ_PROFESSOR_SUBJECT", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professorSubjectIdGenerator")
    @Column(name = "professor_subject_id")
    private Long professorSubjectId;

    /**
     * Bidirectional relationship with {@link Professor}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    @JsonBackReference(value = "professor")
    private Professor professor;

    /**
     * Bidirectional relationship with {@link Subject}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "qualification")
    private Double qualification;

     @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    public Long getProfessorSubjectId() {
        return professorSubjectId;
    }

    public void setProfessorSubjectId(Long professorSubjectId) {
        this.professorSubjectId = professorSubjectId;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Double getQualification() {
        return qualification;
    }

    public void setQualification(Double qualification) {
        this.qualification = qualification;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
}
