package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.dto.request.CourseCreateRequest;
import com.arshdeep.campusflow.dto.request.StudentCreateRequest;
import com.arshdeep.campusflow.dto.request.StudentEditRequest;
import com.arshdeep.campusflow.dto.request.TeacherCreateRequest;
import com.arshdeep.campusflow.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/create/student")
    public String createStudent(@RequestBody StudentCreateRequest studentCreateRequest) {
        return adminService.createStudent(studentCreateRequest);
    }

    @PostMapping("/create/teacher")
    public String createTeacher(@RequestBody TeacherCreateRequest teacherCreateRequest) {
        return adminService.createTeacher(teacherCreateRequest);
    }

    @PostMapping("/create/subject")
    public String createSubject(@RequestBody String subjectName){
        return adminService.createSubject(subjectName);
    }

    @PostMapping("/create/course")
    public String createCourse(@RequestBody CourseCreateRequest courseCreateRequest){
        return adminService.createCourse(courseCreateRequest);
    }

    @PostMapping("/edit/student")
    public String editStudent(@RequestBody StudentEditRequest studentCreateRequest) {
        return adminService.editStudent(studentCreateRequest);
    }

    @PostMapping("/edit/teacher/{teacherId}")
    public String editTeacher(@RequestBody TeacherCreateRequest teacherCreateRequest, @PathVariable Long teacherId) {
        return adminService.editTeacher(teacherCreateRequest, teacherId);
    }

    @PostMapping("/edit/subject/{subjectId}")
    public String editSubject(@RequestBody String subjectName, @PathVariable Long subjectId){
        return adminService.editSubject(subjectName, subjectId);
    }

    @PostMapping("/edit/course/{courseId}")
    public String editCourse(@RequestBody CourseCreateRequest courseCreateRequest, @PathVariable Long courseId){
        return adminService.editCourse(courseCreateRequest, courseId);
    }

    @PostMapping("/delete/student/{studentId}")
    public String deleteStudent(@PathVariable Long studentId) {
        return adminService.deleteStudent(studentId);
    }

    @PostMapping("/delete/teacher/{teacherId}")
    public String deleteTeacher(@PathVariable Long teacherId) {
        return adminService.deleteTeacher(teacherId);
    }

    @PostMapping("/delete/subject/{subjectId}")
    public String deleteSubject(@PathVariable Long subjectId) {
        return adminService.deleteSubject(subjectId);
    }

    @PostMapping("/delete/course/{courseId}")
    public String deleteCourse(@PathVariable Long courseId) {
        return adminService.deleteCourse(courseId);
    }

    @PostMapping("/assign/student/{studentId}/course/{courseId}")
    public String assignStudentToCourse(@PathVariable Long studentId,@PathVariable Long courseId){
        return adminService.assignStudentToCourse(studentId, courseId);
    }

    @PostMapping("/assign/teacher/{teacherId}/subjects")
    public String assignTeacherToSubjects(@PathVariable Long teacherId, @RequestBody Long[] subjectIds){
        return adminService.assignTeacherToSubjects(teacherId, subjectIds);
    }
}
