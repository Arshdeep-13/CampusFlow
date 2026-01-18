package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.dto.request.AttendanceRequest;
import com.arshdeep.campusflow.dto.request.MarksRequest;
import com.arshdeep.campusflow.dto.response.ApiResponse;
import com.arshdeep.campusflow.dto.response.StudentResponse;
import com.arshdeep.campusflow.dto.response.SubjectResponse;
import com.arshdeep.campusflow.entity.Student;
import com.arshdeep.campusflow.entity.Subject;
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

    @GetMapping("/{teacherId}/subjects")
    public ResponseEntity<List<SubjectResponse>> getSubjectsByTeacherId(@PathVariable Integer teacherId){
        List<Subject> subjects = teacherService.getSubjectsByTeacherId(teacherId);
        List<SubjectResponse> responses = subjects.stream()
                .map(subject -> modelMapper.map(subject, SubjectResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{teacherId}/subject/{subjectId}/students")
    public ResponseEntity<List<StudentResponse>> getStudentsOfSubject(@PathVariable Long subjectId, @PathVariable Long teacherId){
        List<Student> students = teacherService.getStudentsOfSubject(subjectId, teacherId);
        List<StudentResponse> responses = students.stream()
                .map(student -> modelMapper.map(student, StudentResponse.class))
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
