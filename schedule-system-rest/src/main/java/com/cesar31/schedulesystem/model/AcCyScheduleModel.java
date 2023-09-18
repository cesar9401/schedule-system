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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ac_cy_schedule_model")
public class AcCyScheduleModel {

    @Id
    @SequenceGenerator(name = "acCyScheduleModelIdGenerator", sequenceName = "SEQ_AC_CY_SCHEDULE_MODEL", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acCyScheduleModelIdGenerator")
    @Column(name = "ac_cy_schedule_model_id")
    private Long acCyScheduleModelId;

    @JoinColumn(name = "academic_cycle_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private AcademicCycle academicCycle;

    @Column(name = "responsible_user")
    @NotNull
    private String responsibleUser;

    @Column(name = "description")
    @NotNull
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtil.DATETIME_FORMAT)
    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    /**
     * Bidirectional relationship with {@link AcCySchedule}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "acCyScheduleModel", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "acCyScheduleModel")
    private List<AcCySchedule> acCySchedules;

    public AcCyScheduleModel() {
        this.entryDate = LocalDateTime.now();
        this.acCySchedules = new ArrayList<>();
    }

    public void clean() {
        this.academicCycle = null;
        this.acCySchedules = new ArrayList<>();
    }

    public Long getAcCyScheduleModelId() {
        return acCyScheduleModelId;
    }

    public void setAcCyScheduleModelId(Long acCyScheduleModelId) {
        this.acCyScheduleModelId = acCyScheduleModelId;
    }

    public AcademicCycle getAcademicCycle() {
        return academicCycle;
    }

    public void setAcademicCycle(AcademicCycle academicCycle) {
        this.academicCycle = academicCycle;
    }

    public String getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
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

    public List<AcCySchedule> getAcCySchedules() {
        return acCySchedules;
    }

    public void setAcCySchedules(List<AcCySchedule> acCySchedules) {
        this.acCySchedules = acCySchedules;
    }
}
