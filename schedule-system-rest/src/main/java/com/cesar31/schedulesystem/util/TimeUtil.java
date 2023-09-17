package com.cesar31.schedulesystem.util;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static LocalTime toLocalTime(LocalDateTime dateTime) {
        return LocalTime.of(dateTime.getHour(), dateTime.getMinute());
    }

    public static LocalDateTime toLocalDateTime(LocalTime time) {
        return LocalDateTime.of(1900, 1, 1, time.getHour(), time.getMinute());
    }
}
