package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.dto.response.AttendanceResponse;
import com.arshdeep.campusflow.dto.response.MarksResponse;
import com.arshdeep.campusflow.entity.Attendance;
import com.arshdeep.campusflow.entity.Marks;
import com.arshdeep.campusflow.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final ModelMapper modelMapper;

    @GetMapping("/attendance/{studentId}")
    public ResponseEntity<List<AttendanceResponse>> getStudentAttendanceInAllSubjects(@PathVariable Long studentId){
        List<Attendance> attendances = studentService.studentAttendanceInAllSubjects(studentId);
        List<AttendanceResponse> responses = attendances.stream()
                .map(attendance -> modelMapper.map(attendance, AttendanceResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/marks/{studentId}")
    public ResponseEntity<List<MarksResponse>> getStudentMarksInAllSubjects(@PathVariable Long studentId){
        List<Marks> marksList = studentService.studentMarksInAllSubjects(studentId);
        List<MarksResponse> responses = marksList.stream()
                .map(marks -> modelMapper.map(marks, MarksResponse.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
