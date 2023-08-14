package com.cesar31.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "ac_cy_sub_class_day_id")
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
