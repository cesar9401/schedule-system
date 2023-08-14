package com.cesar31.schedulesystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "classroom")
public class Classroom {

    @Id
    @SequenceGenerator(name = "classroomIdGenerator", sequenceName = "SEQ_CLASSROOM", allocationSize = 1, initialValue = 10000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classroomIdGenerator")
    @Column(name = "classroom_id")
    private Long classroomId;

    @Column(name = "name")
    private String name;

    @Column(name = "recommended_capacity")
    private Integer recommendedCapacity;

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRecommendedCapacity() {
        return recommendedCapacity;
    }

    public void setRecommendedCapacity(Integer recommendedCapacity) {
        this.recommendedCapacity = recommendedCapacity;
    }
}
