package com.arshdeep.campusflow.dto.request;

import lombok.Data;

@Data
public class StudentCreateRequest {
    private String name;
    private String password;
}
