package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.entity.Attendance;
import com.arshdeep.campusflow.entity.Marks;
import com.arshdeep.campusflow.repository.AttendanceRepository;
import com.arshdeep.campusflow.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/attendance/{studentId}")
    public List<Attendance> getStudentAttendanceInAllSubjects(@PathVariable Long studentId){
        return studentService.studentAttendanceInAllSubjects(studentId);
    }

    @GetMapping("/marks/{studentId}")
    public List<Marks> getStudentMarksInAllSubjects(@PathVariable Long studentId){
        return studentService.studentMarksInAllSubjects(studentId);
    }
}
