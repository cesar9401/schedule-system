package com.cesar31.schedulesystem.dto;

import com.cesar31.schedulesystem.model.AcCySchedule;

public class AcCyScheduleDto {

    private final Long acCyScheduleId;
    private final Long totalNumberOfPeriods;
    private final Long totalCoveredPeriods;

    public AcCyScheduleDto(AcCySchedule schedule) {
        this.acCyScheduleId = schedule.getAcCyScheduleId();
        this.totalNumberOfPeriods = schedule.getTotalNumberOfPeriods();
        this.totalCoveredPeriods = schedule.getTotalCoveredPeriods();
    }

    public Long getAcCyScheduleId() {
        return acCyScheduleId;
    }

    public Long getTotalNumberOfPeriods() {
        return totalNumberOfPeriods;
    }

    public Long getTotalCoveredPeriods() {
        return totalCoveredPeriods;
    }
}
