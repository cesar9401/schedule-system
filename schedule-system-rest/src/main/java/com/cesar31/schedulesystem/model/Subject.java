package com.cesar31.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @SequenceGenerator(name = "subjectIdGenerator", sequenceName = "SEQ_SUBJECT", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subjectIdGenerator")
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "code")
    @NotNull
    private String code;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "number_of_credits")
    @NotNull
    @PositiveOrZero
    private Integer numberOfCredits;

    @Column(name = "required")
    @NotNull
    private Boolean required;

    @Column(name = "subject_index")
    @NotNull
    private Double subjectIndex;

    /**
     * Bidirectional relationship with {@link ProfessorSubject}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "subject")
    private List<ProfessorSubject> professorSubjects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "subject")
    private List<AcCySubject> acCySubjects;

    public Subject() {
        this.professorSubjects = new ArrayList<>();
        this.acCySubjects = new ArrayList<>();
    }

    public void merge(Subject other) {
        this.code = other.code;
        this.name = other.name;
        this.numberOfCredits = other.numberOfCredits;
        this.required = other.required;
        this.subjectIndex = other.subjectIndex;
    }

    public void clean() {
        this.professorSubjects = new ArrayList<>();
        this.acCySubjects = new ArrayList<>();
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfCredits() {
        return numberOfCredits;
    }

    public void setNumberOfCredits(Integer numberOfCredits) {
        this.numberOfCredits = numberOfCredits;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Double getSubjectIndex() {
        return subjectIndex;
    }

    public void setSubjectIndex(Double subjectIndex) {
        this.subjectIndex = subjectIndex;
    }

    public List<ProfessorSubject> getProfessorSubjects() {
        return professorSubjects;
    }

    public void setProfessorSubjects(List<ProfessorSubject> professorSubjects) {
        this.professorSubjects = professorSubjects;
    }

    public List<AcCySubject> getAcCySubjects() {
        return acCySubjects;
    }

    public void setAcCySubjects(List<AcCySubject> acCySubjects) {
        this.acCySubjects = acCySubjects;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Subject subject = (Subject) object;

        return subjectId.equals(subject.subjectId);
    }

    @Override
    public int hashCode() {
        return subjectId.hashCode();
    }
}
