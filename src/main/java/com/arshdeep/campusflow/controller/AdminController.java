package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.dto.request.CourseCreateRequest;
import com.arshdeep.campusflow.dto.request.StudentCreateRequest;
import com.arshdeep.campusflow.dto.request.StudentEditRequest;
import com.arshdeep.campusflow.dto.request.SubjectCreateRequest;
import com.arshdeep.campusflow.dto.request.SubjectEditRequest;
import com.arshdeep.campusflow.dto.request.SubjectIdsRequest;
import com.arshdeep.campusflow.dto.request.TeacherCreateRequest;
import com.arshdeep.campusflow.dto.response.ApiResponse;
import com.arshdeep.campusflow.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create/student")
    public ResponseEntity<ApiResponse> createStudent(@RequestBody StudentCreateRequest studentCreateRequest) {
        String result = adminService.createStudent(studentCreateRequest);
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/create/teacher")
    public ResponseEntity<ApiResponse> createTeacher(@RequestBody TeacherCreateRequest teacherCreateRequest) {
        String result = adminService.createTeacher(teacherCreateRequest);
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/create/subject")
    public ResponseEntity<ApiResponse> createSubject(@RequestBody SubjectCreateRequest subjectCreateRequest){
        String result = adminService.createSubject(subjectCreateRequest.getName());
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/create/course")
    public ResponseEntity<ApiResponse> createCourse(@RequestBody CourseCreateRequest courseCreateRequest){
        String result = adminService.createCourse(courseCreateRequest);
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(true)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/edit/student")
    public ResponseEntity<ApiResponse> editStudent(@RequestBody StudentEditRequest studentEditRequest) {
        String result = adminService.editStudent(studentEditRequest);
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/edit/teacher/{teacherId}")
    public ResponseEntity<ApiResponse> editTeacher(@RequestBody TeacherCreateRequest teacherCreateRequest, @PathVariable Long teacherId) {
        String result = adminService.editTeacher(teacherCreateRequest, teacherId);
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/edit/subject/{subjectId}")
    public ResponseEntity<ApiResponse> editSubject(@RequestBody SubjectEditRequest subjectEditRequest, @PathVariable Long subjectId){
        String result = adminService.editSubject(subjectEditRequest.getName(), subjectId);
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/edit/course/{courseId}")
    public ResponseEntity<ApiResponse> editCourse(@RequestBody CourseCreateRequest courseCreateRequest, @PathVariable Long courseId){
        String result = adminService.editCourse(courseCreateRequest, courseId);
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/delete/student/{studentId}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable Long studentId) {
        String result = adminService.deleteStudent(studentId);
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/delete/teacher/{teacherId}")
    public ResponseEntity<ApiResponse> deleteTeacher(@PathVariable Long teacherId) {
        String result = adminService.deleteTeacher(teacherId);
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/delete/subject/{subjectId}")
    public ResponseEntity<ApiResponse> deleteSubject(@PathVariable Long subjectId) {
        String result = adminService.deleteSubject(subjectId);
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/delete/course/{courseId}")
    public ResponseEntity<ApiResponse> deleteCourse(@PathVariable Long courseId) {
        String result = adminService.deleteCourse(courseId);
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/assign/student/{studentId}/course/{courseId}")
    public ResponseEntity<ApiResponse> assignStudentToCourse(@PathVariable Long studentId,@PathVariable Long courseId){
        String result = adminService.assignStudentToCourse(studentId, courseId);
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @PostMapping("/assign/teacher/{teacherId}/subjects")
    public ResponseEntity<ApiResponse> assignTeacherToSubjects(@PathVariable Long teacherId, @RequestBody SubjectIdsRequest subjectIdsRequest){
        String result = adminService.assignTeacherToSubjects(teacherId, subjectIdsRequest.getSubjectIds());
        boolean success = !result.contains("not found");
        ApiResponse response = ApiResponse.builder()
                .message(result)
                .success(success)
                .build();
        return success ? ResponseEntity.ok(response) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
