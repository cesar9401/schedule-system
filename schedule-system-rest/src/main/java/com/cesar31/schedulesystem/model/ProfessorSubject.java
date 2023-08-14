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

@Entity
@Table(name = "professor_subject")
public class ProfessorSubject {

    @Id
    @SequenceGenerator(name = "professorSubjectIdGenerator", sequenceName = "SEQ_PROFESSOR_SUBJECT", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professorSubjectIdGenerator")
    @Column(name = "professor")
    private Long professorSubjectId;

    /**
     * Bidirectional relationship with {@link Professor}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    @JsonBackReference(value = "prefessor")
    private Professor professor;

    /**
     * Bidirectional relationship with {@link Subject}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @JsonBackReference(value = "subject")
    private Subject subject;

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
}
