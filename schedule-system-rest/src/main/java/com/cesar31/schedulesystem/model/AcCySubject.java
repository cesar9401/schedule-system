package com.cesar31.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "number_of_periods")
    private Integer numberOfPeriods;

    @Column(name = "assigned_students")
    private Integer assignedStudents;

    /**
     * Bidirectional relationship with {@link AcCySubClassDay}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acCySubject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "acCySubject")
    private List<AcCySubClassDay> acCySubClassDays;

    /**
     * Bidirectional relationship with {@link AcCySubjAssg}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acCySubject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "acCySubject")
    private List<AcCySubjAssg> acCySubjAssignments;

    public AcCySubject() {
        this.priority = 1;
        this.acCySubClassDays = new ArrayList<>();
        this.acCySubjAssignments = new ArrayList<>();
    }

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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getNumberOfPeriods() {
        return numberOfPeriods;
    }

    public void setNumberOfPeriods(Integer numberOfPeriods) {
        this.numberOfPeriods = numberOfPeriods;
    }

    public Integer getAssignedStudents() {
        return assignedStudents;
    }

    public void setAssignedStudents(Integer assignedStudents) {
        this.assignedStudents = assignedStudents;
    }

    public List<AcCySubClassDay> getAcCySubClassDays() {
        return acCySubClassDays;
    }

    public void setAcCySubClassDays(List<AcCySubClassDay> acCySubClassDays) {
        this.acCySubClassDays = acCySubClassDays;
    }

    public List<AcCySubjAssg> getAcCySubjAssignments() {
        return acCySubjAssignments;
    }

    public void setAcCySubjAssignments(List<AcCySubjAssg> acCySubjAssignments) {
        this.acCySubjAssignments = acCySubjAssignments;
    }

    @Override
    public String toString() {
        return "AcCySubject{" +
                "subject=" + subject.getName() +
                ", sectionCode='" + sectionCode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        AcCySubject subject = (AcCySubject) object;

        return acCySubjectId.equals(subject.acCySubjectId);
    }

    @Override
    public int hashCode() {
        return acCySubjectId.hashCode();
    }
}
