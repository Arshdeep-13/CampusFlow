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
public class AttendanceResponse {
    private Long id;
    private boolean present;
    private Long markedById;
    private Long studentId;
    private String studentName;
    private Long subjectId;
    private String subjectName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
