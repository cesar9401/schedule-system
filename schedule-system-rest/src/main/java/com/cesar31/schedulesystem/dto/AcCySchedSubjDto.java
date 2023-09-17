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


    /**
     * Art-1 - Artes
     * Sección: A
     * Docente: Luis Miguel
     */
    public String schedSubjNoClassroomStr() {
        var builder = new StringBuilder();

        if (acCySubject != null) {
            builder.append(acCySubject.getSubject().getCode())
                    .append(" - ")
                    .append(acCySubject.getSubject().getName())
                    .append("\n")
                    .append("Sección: ")
                    .append(acCySubject.getSectionCode())
                    .append("\n");
        }

        if (professor != null) {
            builder
                    .append("Docente: ")
                    .append(professor.getFullName());
        }

        return builder.toString();
    }

    public String schedSubjNoProfessor() {
        var builder = new StringBuilder();

        if (acCySubject != null) {
            builder.append(acCySubject.getSubject().getCode())
                    .append(" - ")
                    .append(acCySubject.getSubject().getName())
                    .append("\n")
                    .append("Sección: ")
                    .append(acCySubject.getSectionCode())
                    .append("\n");
        }

        if (classroom != null) {
            builder.append("Salón: ")
                    .append(classroom.getName());
        }

        return builder.toString();
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
