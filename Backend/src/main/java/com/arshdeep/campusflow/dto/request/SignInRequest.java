package com.arshdeep.campusflow.dto.request;

import com.arshdeep.campusflow.entity.type.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
    private RoleType role;
}
