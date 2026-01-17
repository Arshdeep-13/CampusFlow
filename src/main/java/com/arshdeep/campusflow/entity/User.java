package com.arshdeep.campusflow.entity;

import com.arshdeep.campusflow.entity.type.RoleType;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User extends  BaseEntity {
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
