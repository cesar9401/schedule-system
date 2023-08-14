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
@Table(name = "academic_cycle")
public class AcademicCycle {

    @Id
    @SequenceGenerator(name = "academicCycleIdGenerator", sequenceName = "SEQ_ACADEMIC_CYCLE", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "academicCycleIdGenerator")
    @Column(name = "academic_cycle_id")
    private Long academicCycleId;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * Bidirectional relationship with {@link AcCyClassDay}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "academicCycle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "academicCycle")
    private List<AcCyClassDay> classDays;

    /**
     * Bidirectional relationship with {@link AcCySubject}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "academicCycle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "academicCycle")
    private List<AcCySubject> acCySubjects;

    public Long getAcademicCycleId() {
        return academicCycleId;
    }

    public void setAcademicCycleId(Long academicCycleId) {
        this.academicCycleId = academicCycleId;
    }

    public LocalDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDateTime entryDate) {
        this.entryDate = entryDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
