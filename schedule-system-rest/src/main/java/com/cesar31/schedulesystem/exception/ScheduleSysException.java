package com.cesar31.schedulesystem.exception;

import javax.ws.rs.core.Response;

public class ScheduleSysException extends Exception {

    private Integer status;

    /**
     * To avoid create schedule system exception without message
     */
    private ScheduleSysException() {
    }

    public ScheduleSysException(String message) {
        super(message);
    }

    public Integer getStatus() {
        return status;
    }

    public ScheduleSysException status(Integer status) {
        this.status = status;
        return this;
    }

    public ScheduleSysException status(Response.Status status) {
        this.status = status.getStatusCode();
        return this;
    }
}
