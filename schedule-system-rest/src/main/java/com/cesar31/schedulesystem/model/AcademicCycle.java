package com.cesar31.schedulesystem.model;

import com.cesar31.schedulesystem.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
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
import javax.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "academic_cycle")
public class AcademicCycle {

    @Id
    @SequenceGenerator(name = "academicCycleIdGenerator", sequenceName = "SEQ_ACADEMIC_CYCLE", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "academicCycleIdGenerator")
    @Column(name = "academic_cycle_id")
    private Long academicCycleId;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "description")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtil.DATETIME_FORMAT)
    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtil.DATETIME_FORMAT)
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtil.DATETIME_FORMAT)
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

    public AcademicCycle() {
        this.entryDate = LocalDateTime.now();
        this.startDate = LocalDateTime.now();
        this.endDate = LocalDateTime.now();

        this.classDays = new ArrayList<>();
        this.acCySubjects = new ArrayList<>();
    }

    public Long getAcademicCycleId() {
        return academicCycleId;
    }

    public void setAcademicCycleId(Long academicCycleId) {
        this.academicCycleId = academicCycleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<AcCyClassDay> getClassDays() {
        return classDays;
    }

    public void setClassDays(List<AcCyClassDay> classDays) {
        this.classDays = classDays;
    }

    public List<AcCySubject> getAcCySubjects() {
        return acCySubjects;
    }

    public void setAcCySubjects(List<AcCySubject> acCySubjects) {
        this.acCySubjects = acCySubjects;
    }
}
