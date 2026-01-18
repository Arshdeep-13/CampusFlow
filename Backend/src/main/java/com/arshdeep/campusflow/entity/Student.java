package com.arshdeep.campusflow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
