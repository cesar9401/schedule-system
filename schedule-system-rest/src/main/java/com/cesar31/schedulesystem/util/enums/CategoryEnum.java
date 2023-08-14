package com.cesar31.schedulesystem.util.enums;

public enum CategoryEnum {

    DAY_OF_WEEK(10L);

    CategoryEnum(Long internalId) {
        this.internalId = internalId;
    }

    public final Long internalId;
}
