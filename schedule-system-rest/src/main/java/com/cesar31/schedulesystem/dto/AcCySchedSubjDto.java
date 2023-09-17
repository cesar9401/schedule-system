package com.cesar31.schedulesystem.dto;

import com.cesar31.schedulesystem.model.AcCySchedSubj;
import com.cesar31.schedulesystem.model.AcCySubject;
import com.cesar31.schedulesystem.model.Classroom;
import com.cesar31.schedulesystem.model.Professor;

public class AcCySchedSubjDto {

    private final Classroom classroom;
    private final Professor professor;
    private final AcCySubject acCySubject;
    private final PeriodDto period;

    public AcCySchedSubjDto(AcCySchedSubj entity) {
        this.classroom = entity.getClassroom();
        this.professor = entity.getProfessor();
        this.acCySubject = entity.getAcCySubject();
        this.period = new PeriodDto(entity.getCatDay(), entity.getStartTime(), entity.getEndTime());
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public Professor getProfessor() {
        return professor;
    }

    public AcCySubject getAcCySubject() {
        return acCySubject;
    }

    public PeriodDto getPeriod() {
        return period;
    }
}
