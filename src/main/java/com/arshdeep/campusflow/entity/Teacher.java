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
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
