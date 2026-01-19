package com.arshdeep.campusflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {
    private Long id;
    private String name;
    private Long courseId;
    private String courseName;
    private String courseCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
