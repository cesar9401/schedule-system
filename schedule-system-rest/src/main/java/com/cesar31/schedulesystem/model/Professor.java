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

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @SequenceGenerator(name = "professorIdGenerator", sequenceName = "SEQ_PROFESSOR", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professorIdGenerator")
    @Column(name = "prefessor_id")
    private Long professorId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_hire")
    private LocalDateTime dateOfHire;

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
}
