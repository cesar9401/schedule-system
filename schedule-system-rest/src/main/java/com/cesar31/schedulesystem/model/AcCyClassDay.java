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

import java.time.LocalDateTime;

@Entity
@Table(name = "ac_cy_class_day")
public class AcCyClassDay {

    @Id
    @SequenceGenerator(name = "acCyClassDayIdGenerator", sequenceName = "SEQ_AC_CY_CLASS_DAY", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acCyClassDayIdGenerator")
    @Column(name = "ac_cy_class_day_id")
    private Long acCyClassDayId;

    /**
     * Bidirectional relationship with {@link AcademicCycle}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_cycle_id")
    @JsonBackReference(value = "academicCycle")
    private AcademicCycle academicCycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_day")
    private Category catDay;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    public Long getAcCyClassDayId() {
        return acCyClassDayId;
    }

    public void setAcCyClassDayId(Long acCyClassDayId) {
        this.acCyClassDayId = acCyClassDayId;
    }

    public AcademicCycle getAcademicCycle() {
        return academicCycle;
    }

    public void setAcademicCycle(AcademicCycle academicCycle) {
        this.academicCycle = academicCycle;
    }

    public Category getCatDay() {
        return catDay;
    }

    public void setCatDay(Category catDay) {
        this.catDay = catDay;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
