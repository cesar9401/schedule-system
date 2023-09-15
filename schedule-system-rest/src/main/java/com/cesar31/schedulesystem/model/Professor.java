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

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @SequenceGenerator(name = "professorIdGenerator", sequenceName = "SEQ_PROFESSOR", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professorIdGenerator")
    @Column(name = "professor_id")
    private Long professorId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_hire")
    private LocalDateTime dateOfHire;

    @Column(name = "average_qualification")
    private Double averageQualification;

    /**
     * Bidirectional relationship with {@link ProfessorContractDay}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "professor")
    private List<ProfessorContractDay> contractDays;

    /**
     * Bidirectional relationship with {@link ProfessorSubject}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "professor")
    private List<ProfessorSubject> professorSubjects;

    /**
     * Bidirectional relationship with {@link AcCySchedSubj}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "professor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "professor")
    private List<AcCySchedSubj> acCySchedSubjs;

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDateOfHire() {
        return dateOfHire;
    }

    public void setDateOfHire(LocalDateTime dateOfHire) {
        this.dateOfHire = dateOfHire;
    }

    public Double getAverageQualification() {
        return averageQualification;
    }

    public void setAverageQualification(Double averageQualification) {
        this.averageQualification = averageQualification;
    }

    public List<ProfessorContractDay> getContractDays() {
        return contractDays;
    }

    public void setContractDays(List<ProfessorContractDay> contractDays) {
        this.contractDays = contractDays;
    }

    public List<ProfessorSubject> getProfessorSubjects() {
        return professorSubjects;
    }

    public void setProfessorSubjects(List<ProfessorSubject> professorSubjects) {
        this.professorSubjects = professorSubjects;
    }

    public List<AcCySchedSubj> getSubjectSchedules() {
        return acCySchedSubjs;
    }

    public void setSubjectSchedules(List<AcCySchedSubj> acCySchedSubjs) {
        this.acCySchedSubjs = acCySchedSubjs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Professor professor = (Professor) o;

        return professorId.equals(professor.professorId);
    }

    @Override
    public int hashCode() {
        return professorId.hashCode();
    }
}
