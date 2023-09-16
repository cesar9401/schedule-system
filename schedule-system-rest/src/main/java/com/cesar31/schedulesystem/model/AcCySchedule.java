package com.cesar31.schedulesystem.model;

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
import java.util.stream.Collectors;

@Entity
@Table(name = "ac_cy_schedule")
public class AcCySchedule {

    @Id
    @SequenceGenerator(name = "acCyScheduleIdGenerator", sequenceName = "SEQ_AC_CY_SCHEDULE", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acCyScheduleIdGenerator")
    @Column(name = "ac_cy_schedule_id")
    private Long acCyScheduleId;

    @JoinColumn(name = "academic_cycle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AcademicCycle academicCycle;

    @Column(name = "is_valid")
    private Boolean isValid;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acCySchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "acCySchedule")
    private List<AcCySchedSubj> acCySchedSubjs;

    public AcCySchedule() {
        this.isValid = true;
        this.acCySchedSubjs = new ArrayList<>();
    }

    public AcCySchedule(AcCySchedule other) {
        this.academicCycle = other.academicCycle;
        this.isValid = other.isValid;
        this.acCySchedSubjs = other
                .acCySchedSubjs
                .stream()
                .map(AcCySchedSubj::new)
                .collect(Collectors.toList());
    }

    public Long getAcCyScheduleId() {
        return acCyScheduleId;
    }

    public void setAcCyScheduleId(Long acCyScheduleId) {
        this.acCyScheduleId = acCyScheduleId;
    }

    public AcademicCycle getAcademicCycle() {
        return academicCycle;
    }

    public void setAcademicCycle(AcademicCycle academicCycle) {
        this.academicCycle = academicCycle;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public List<AcCySchedSubj> getAcCySchedSubjs() {
        return acCySchedSubjs;
    }

    public void setAcCySchedSubjs(List<AcCySchedSubj> acCySchedSubjs) {
        this.acCySchedSubjs = acCySchedSubjs;
    }
}
