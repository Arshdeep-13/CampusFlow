package com.arshdeep.campusflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student extends  BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String rollNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}
