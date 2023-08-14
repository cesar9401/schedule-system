package com.cesar31.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "ac_cy_subject")
public class AcCySubject {

    @Id
    @SequenceGenerator(name = "acCySubjectIdGenerator", sequenceName = "SEQ_AC_CY_SUBJECT", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acCySubjectIdGenerator")
    @Column(name = "ac_cy_subject_id")
    private Long acCySubjectId;

    /**
     * Bidirectional relationship with {@link AcademicCycle}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_cycle_id")
    @JsonBackReference(value = "academicCycle")
    private AcademicCycle academicCycle;

    /**
     * Unidirectional relationship with {@link Subject}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(name = "section_code")
    private String sectionCode;

    /**
     * Bidirectional relationship with {@link AcCySubClassDay}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acCySubject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "acCySubject")
    private List<AcCySubClassDay> acCySubClassDays;

    public Long getAcCySubjectId() {
        return acCySubjectId;
    }

    public void setAcCySubjectId(Long acCySubjectId) {
        this.acCySubjectId = acCySubjectId;
    }

    public AcademicCycle getAcademicCycle() {
        return academicCycle;
    }

    public void setAcademicCycle(AcademicCycle academicCycle) {
        this.academicCycle = academicCycle;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public List<AcCySubClassDay> getAcCySubClassDays() {
        return acCySubClassDays;
    }

    public void setAcCySubClassDays(List<AcCySubClassDay> acCySubClassDays) {
        this.acCySubClassDays = acCySubClassDays;
    }
}
