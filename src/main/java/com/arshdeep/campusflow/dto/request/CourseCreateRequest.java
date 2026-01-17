package com.arshdeep.campusflow.dto.request;

import lombok.Data;

@Data
public class CourseCreateRequest {
    private String courseName;
    private String courseCode;
}
