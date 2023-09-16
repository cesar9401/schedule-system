package com.cesar31.schedulesystem.dto;

import com.cesar31.schedulesystem.model.Category;
import com.cesar31.schedulesystem.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PeriodDto {

    private final Category catDay;
    private final LocalTime startTime;
    private final LocalTime endTime;

    public PeriodDto(Category catDay, LocalDateTime startTime, LocalDateTime endTime) {
        this.catDay = catDay;
        this.startTime = TimeUtil.toLocalTime(startTime);
        this.endTime = TimeUtil.toLocalTime(endTime);
    }

    public Category getCatDay() {
        return catDay;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        PeriodDto periodDto = (PeriodDto) object;

        if (!catDay.equals(periodDto.catDay)) return false;
        if (!startTime.equals(periodDto.startTime)) return false;
        return endTime.equals(periodDto.endTime);
    }

    @Override
    public int hashCode() {
        int result = catDay.hashCode();
        result = 31 * result + startTime.hashCode();
        result = 31 * result + endTime.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "PeriodDto{" +
                "catDay=" + catDay.getDescription() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
