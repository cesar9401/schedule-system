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
@Table(name = "ac_cy_subj_assg")
public class AcCySubjAssg {

    @Id
    @SequenceGenerator(name = "acCySubjAssgIdGenerator", sequenceName = "SEQ_AC_CY_SUBJ_ASSG", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acCySubjAssgIdGenerator")
    @Column(name = "ac_cy_subj_assg_id")
    private Long acCySubjAssgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ac_cy_subject_id")
    @JsonBackReference(value = "acCySubject")
    private AcCySubject acCySubject;

    /**
     * Bidirectional relationship with {@link Student}
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    @JsonBackReference(value = "student")
    private Student student;
}
