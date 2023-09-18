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
import java.util.stream.Collectors;

@Entity
@Table(name = "ac_cy_schedule")
public class AcCySchedule {

    @Id
    @SequenceGenerator(name = "acCyScheduleIdGenerator", sequenceName = "SEQ_AC_CY_SCHEDULE", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acCyScheduleIdGenerator")
    @Column(name = "ac_cy_schedule_id")
    private Long acCyScheduleId;

    /**
     * Bidirectional relationship with {@link AcCyScheduleModel}
     */
    @JoinColumn(name = "ac_cy_schedule_model_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "acCyScheduleModel")
    private AcCyScheduleModel acCyScheduleModel;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "total_number_of_periods")
    private Long totalNumberOfPeriods;

    @Column(name = "total_covered_periods")
    private Long totalCoveredPeriods;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acCySchedule", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "acCySchedule")
    private List<AcCySchedSubj> acCySchedSubjs;

    public AcCySchedule() {
        this.isValid = true;
        this.acCySchedSubjs = new ArrayList<>();
    }

    public AcCySchedule(AcCySchedule other) {
        this.acCyScheduleModel = other.acCyScheduleModel;
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

    public AcCyScheduleModel getAcCyScheduleModel() {
        return acCyScheduleModel;
    }

    public void setAcCyScheduleModel(AcCyScheduleModel acCyScheduleModel) {
        this.acCyScheduleModel = acCyScheduleModel;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public Long getTotalNumberOfPeriods() {
        return totalNumberOfPeriods;
    }

    public void setTotalNumberOfPeriods(Long totalNumberOfPeriods) {
        this.totalNumberOfPeriods = totalNumberOfPeriods;
    }

    public Long getTotalCoveredPeriods() {
        return totalCoveredPeriods;
    }

    public void setTotalCoveredPeriods(Long totalCoveredPeriods) {
        this.totalCoveredPeriods = totalCoveredPeriods;
    }

    public List<AcCySchedSubj> getAcCySchedSubjs() {
        return acCySchedSubjs;
    }

    public void setAcCySchedSubjs(List<AcCySchedSubj> acCySchedSubjs) {
        this.acCySchedSubjs = acCySchedSubjs;
    }
}
