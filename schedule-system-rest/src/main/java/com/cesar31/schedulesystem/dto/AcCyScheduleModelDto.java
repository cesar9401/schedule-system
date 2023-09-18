package com.cesar31.schedulesystem.dto;

import com.cesar31.schedulesystem.model.AcCyScheduleModel;
import com.cesar31.schedulesystem.util.TimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class AcCyScheduleModelDto {

    private Long acCyScheduleModelId;
    private String academicCycleName;
    private String responsibleUser;
    private String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TimeUtil.DATETIME_FORMAT)
    private LocalDateTime entryDate;
    private Integer generatedSchedules;

    public AcCyScheduleModelDto(AcCyScheduleModel entity) {
        this.acCyScheduleModelId = entity.getAcCyScheduleModelId();
        this.academicCycleName = entity.getAcademicCycle().getName();
        this.responsibleUser = entity.getResponsibleUser();
        this.description = entity.getDescription();
        this.entryDate = entity.getEntryDate();
        this.generatedSchedules = entity.getAcCySchedules().size();
    }

    public Long getAcCyScheduleModelId() {
        return acCyScheduleModelId;
    }

    public void setAcCyScheduleModelId(Long acCyScheduleModelId) {
        this.acCyScheduleModelId = acCyScheduleModelId;
    }

    public String getAcademicCycleName() {
        return academicCycleName;
    }

    public void setAcademicCycleName(String academicCycleName) {
        this.academicCycleName = academicCycleName;
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

    public Integer getGeneratedSchedules() {
        return generatedSchedules;
    }

    public void setGeneratedSchedules(Integer generatedSchedules) {
        this.generatedSchedules = generatedSchedules;
    }
}
