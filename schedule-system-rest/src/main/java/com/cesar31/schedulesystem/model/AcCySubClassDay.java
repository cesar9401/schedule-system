package com.cesar31.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ac_cy_sub_class_day")
public class AcCySubClassDay {

    @Id
    @SequenceGenerator(name = "acCySubClassDayIdGenerator", sequenceName = "SEQ_AC_CY_SUB_CLASS_DAY", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acCySubClassDayIdGenerator")
    @Column(name = "ac_cy_sub_class_day_id")
    private Long acCySubClassDayId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_day")
    private Category catDay;

    /**
     * Bidirectional relationship with {@link AcCySubject}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ac_cy_subject_id")
    @JsonBackReference(value = "acCySubject")
    private AcCySubject acCySubject;

    public Long getAcCySubClassDayId() {
        return acCySubClassDayId;
    }

    public void setAcCySubClassDayId(Long acCySubClassDayId) {
        this.acCySubClassDayId = acCySubClassDayId;
    }

    public Category getCatDay() {
        return catDay;
    }

    public void setCatDay(Category catDay) {
        this.catDay = catDay;
    }

    public AcCySubject getAcCySubject() {
        return acCySubject;
    }

    public void setAcCySubject(AcCySubject acCySubject) {
        this.acCySubject = acCySubject;
    }
}
