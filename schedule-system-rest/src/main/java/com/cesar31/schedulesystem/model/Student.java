package com.cesar31.schedulesystem.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @SequenceGenerator(name = "studentIdGenerator", sequenceName = "SEQ_STUDENT", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentIdGenerator")
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    /**
     * Bidirectional relationship with {@link AcCySubjAssg}
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "student")
    private List<AcCySubjAssg> acCySubjAssignments;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
