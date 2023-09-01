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
     * Bidirectional relationship with {@link Subject}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    @JsonBackReference(value = "academicCycle")
    private Subject subject;

    @Column(name = "section_code")
    private String sectionCode;

    @Column(name = "priority")
    private Integer priority;

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

    /**
     * Bidirectional relationship with {@link AcCySchedSubj}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acCySubject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "acCySubject")
    private List<AcCySchedSubj> acCySchedSubjs;

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

    public List<AcCySchedSubj> getSubjectSchedules() {
        return acCySchedSubjs;
    }

    public void setSubjectSchedules(List<AcCySchedSubj> acCySchedSubjs) {
        this.acCySchedSubjs = acCySchedSubjs;
    }
}
