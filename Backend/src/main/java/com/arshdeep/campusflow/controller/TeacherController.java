package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.dto.request.AttendanceRequest;
import com.arshdeep.campusflow.dto.request.MarksRequest;
import com.arshdeep.campusflow.dto.response.ApiResponse;
import com.arshdeep.campusflow.dto.response.StudentResponse;
import com.arshdeep.campusflow.dto.response.SubjectResponse;
import com.arshdeep.campusflow.dto.response.TeacherCountResponse;
import com.arshdeep.campusflow.entity.Student;
import com.arshdeep.campusflow.entity.Subject;
import com.arshdeep.campusflow.entity.Teacher;
import com.arshdeep.campusflow.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherService.getAllTeachers();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/count")
    public ResponseEntity<TeacherCountResponse> teacherCount(Integer teacherCount) {
        return ResponseEntity.ok(teacherService.getTeacherCount());
    }

    @GetMapping("/{teacherId}/subjects")
    public ResponseEntity<List<SubjectResponse>> getSubjectsByTeacherId(@PathVariable Integer teacherId){
        try {
            List<Subject> subjects = teacherService.getSubjectsByTeacherId(teacherId);
            List<SubjectResponse> responses = subjects.stream()
                    .map(subject -> {
                        // Manually map to avoid ModelMapper issues with students field
                        SubjectResponse response = SubjectResponse.builder()
                                .id(subject.getId())
                                .name(subject.getName())
                                .createdAt(subject.getCreatedAt())
                                .updatedAt(subject.getUpdatedAt())
                                .courseId(subject.getCourse() != null ? subject.getCourse().getId() : null)
                                .courseName(subject.getCourse() != null ? subject.getCourse().getName() : null)
                                .courseCode(subject.getCourse() != null ? subject.getCourse().getCourseCode() : null)
                                .teacherId(subject.getTeacher() != null ? subject.getTeacher().getId() : null)
                                .teacherName(subject.getTeacher() != null ? subject.getTeacher().getName() : null)
                                .build();
                        
                        // Include students from the course
                        if (subject.getCourse() != null && subject.getCourse().getStudents() != null) {
                            response.setStudents(subject.getCourse().getStudents().stream()
                                    .map(student -> StudentResponse.builder()
                                            .id(student.getId())
                                            .name(student.getName())
                                            .createdAt(student.getCreatedAt())
                                            .updatedAt(student.getUpdatedAt())
                                            .courseId(student.getCourse() != null ? student.getCourse().getId() : null)
                                            .courseName(student.getCourse() != null ? student.getCourse().getName() : null)
                                            .courseCode(student.getCourse() != null ? student.getCourse().getCourseCode() : null)
                                            .build())
                                    .collect(Collectors.toList()));
                        } else {
                            response.setStudents(List.of());
                        }
                        return response;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{teacherId}/subject/{subjectId}/students")
    public ResponseEntity<List<StudentResponse>> getStudentsOfSubject(@PathVariable Long subjectId, @PathVariable Long teacherId){
        List<Student> students = teacherService.getStudentsOfSubject(subjectId, teacherId);
        List<StudentResponse> responses = students.stream()
                .map(student -> StudentResponse.builder()
                        .id(student.getId())
                        .name(student.getName())
                        .createdAt(student.getCreatedAt())
                        .updatedAt(student.getUpdatedAt())
                        .courseId(student.getCourse() != null ? student.getCourse().getId() : null)
                        .courseName(student.getCourse() != null ? student.getCourse().getName() : null)
                        .courseCode(student.getCourse() != null ? student.getCourse().getCourseCode() : null)
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PostMapping("/{teacherId}/subject/{subjectId}/student/{studentId}/attendance")
    public ResponseEntity<ApiResponse> markStudentAttendance(@PathVariable Long teacherId, @PathVariable Long subjectId, @PathVariable Long studentId, @RequestBody AttendanceRequest attendanceRequest){
        String result = teacherService.markStudentAttendance(teacherId, subjectId, studentId, attendanceRequest.isPresent());
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/{teacherId}/subject/{subjectId}/student/{studentId}/marks")
    public ResponseEntity<ApiResponse> markStudentMarks(@PathVariable Long teacherId, @PathVariable Long subjectId, @PathVariable Long studentId, @RequestBody MarksRequest marksRequest){
        String result = teacherService.markStudentMarks(teacherId, subjectId, studentId, marksRequest.getMarks());
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(response);
    }
}
