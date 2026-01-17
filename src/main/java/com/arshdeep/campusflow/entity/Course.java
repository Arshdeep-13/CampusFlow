package com.arshdeep.campusflow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "courses")
public class Course extends BaseEntity{
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String courseCode;

    @OneToMany(mappedBy = "course")
    List<Student> students;

    @OneToMany(mappedBy = "course")
    @JsonIgnore
    List<Subject> subjects;
}
