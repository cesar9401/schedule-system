package com.cesar31.schedulesystem.util.comparator;

import com.cesar31.schedulesystem.model.AcCySubject;
import com.cesar31.schedulesystem.util.enums.CategoryEnum;

import java.util.Comparator;

public class AcCySubjectComparator implements Comparator<AcCySubject> {

    private final CategoryEnum subjectOrder;

    public AcCySubjectComparator(CategoryEnum subjectOrder) {
        this.subjectOrder = subjectOrder;
    }

    @Override
    public int compare(AcCySubject o1, AcCySubject o2) {
        var s1 = o1.getSubject();
        var s2 = o2.getSubject();

        switch (subjectOrder) {
            case SO_CREDITS:
                return s1.getNumberOfCredits().compareTo(s2.getNumberOfCredits());
            case SO_CREDITS_REVERSE:
                return s2.getNumberOfCredits().compareTo(s1.getNumberOfCredits());
            case SO_REQUIRED:
                return s1.getRequired().compareTo(s2.getRequired());
            case SO_REQUIRED_REVERSE:
                return s2.getRequired().compareTo(s1.getRequired());
            case SO_INDEX:
                return s1.getSubjectIndex().compareTo(s2.getSubjectIndex());
            case SO_INDEX_REVERSE:
                return s2.getSubjectIndex().compareTo(s1.getSubjectIndex());
            case SO_PERIODS:
                return o1.getNumberOfPeriods().compareTo(o2.getNumberOfPeriods());
            case SO_PERIODS_REV:
                return o2.getNumberOfPeriods().compareTo(o1.getNumberOfPeriods());
            case SO_ASSIGNED_STUDENTS:
                return o1.getAssignedStudents().compareTo(o2.getAssignedStudents());
            case SO_ASSIGNED_STUDENTS_REV:
                return o2.getAssignedStudents().compareTo(o1.getAssignedStudents());
        }

        return 0;
    }
}
