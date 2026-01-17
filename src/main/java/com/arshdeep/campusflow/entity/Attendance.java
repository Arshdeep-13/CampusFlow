package com.arshdeep.campusflow.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "attendances")
public class Attendance extends  BaseEntity {
    @Column(nullable = false)
    private boolean present;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

}
