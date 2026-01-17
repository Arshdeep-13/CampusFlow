package com.arshdeep.campusflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "teachers")
public class Teacher extends  BaseEntity {
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String employeeId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    List<Subject> subjects;
}
