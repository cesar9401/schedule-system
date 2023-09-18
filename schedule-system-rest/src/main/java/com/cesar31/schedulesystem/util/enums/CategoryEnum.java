package com.cesar31.schedulesystem.util.enums;

public enum CategoryEnum {

    DAY_OF_WEEK(10L),
        DW_MONDAY(11L),
    SUBJECT_ORDER(20L),
        SO_CREDITS(21L),
        SO_CREDITS_REVERSE(22L),
        SO_REQUIRED(23L),
        SO_REQUIRED_REVERSE(24L),
        SO_INDEX(25L),
        SO_INDEX_REVERSE(26L),
        SO_PERIODS(27L),
        SO_PERIODS_REV(28L),
        SO_ASSIGNED_STUDENTS(29L),
        SO_ASSIGNED_STUDENTS_REV(30L),
        SO_SHUFFLE(31L)
    ;

    CategoryEnum(Long internalId) {
        this.internalId = internalId;
    }

    public final Long internalId;
}
