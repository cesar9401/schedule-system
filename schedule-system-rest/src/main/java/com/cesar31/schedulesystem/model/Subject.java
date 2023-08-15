package com.cesar31.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

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
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "number_of_credits")
    private Integer numberOfCredits;

    @Column(name = "required")
    private Boolean required;

    /**
     * Bidirectional relationship with {@link ProfessorSubject}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "subject")
    private List<ProfessorSubject> professorSubjects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "subject")
    private List<AcCySubject> acCySubjects;

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

    public List<ProfessorSubject> getProfessorSubjects() {
        return professorSubjects;
    }

    public void setProfessorSubjects(List<ProfessorSubject> professorSubjects) {
        this.professorSubjects = professorSubjects;
    }
}
