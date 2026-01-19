package com.arshdeep.campusflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectResponse {
    private Long id;
    private String name;
    private Long courseId;
    private String courseName;
    private String courseCode;
    private Long teacherId;
    private String teacherName;
    private List<StudentResponse> students;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
