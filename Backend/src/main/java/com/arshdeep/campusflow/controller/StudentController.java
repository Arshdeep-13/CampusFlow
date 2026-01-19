package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.dto.response.AttendanceResponse;
import com.arshdeep.campusflow.dto.response.MarksResponse;
import com.arshdeep.campusflow.dto.response.StudentCountResponse;
import com.arshdeep.campusflow.entity.Attendance;
import com.arshdeep.campusflow.entity.Marks;
import com.arshdeep.campusflow.entity.Student;
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

    @GetMapping("/count")
    public ResponseEntity<StudentCountResponse> getStudentCount() {
        return ResponseEntity.ok(studentService.getStudentCount());
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/attendance/{studentId}")
    public ResponseEntity<List<AttendanceResponse>> getStudentAttendanceInAllSubjects(@PathVariable Long studentId){
        List<Attendance> attendances = studentService.studentAttendanceInAllSubjects(studentId);
        List<AttendanceResponse> responses = attendances.stream()
                .map(attendance -> AttendanceResponse.builder()
                        .id(attendance.getId())
                        .present(attendance.isPresent())
                        .markedById(attendance.getMarkedById())
                        .createdAt(attendance.getCreatedAt())
                        .updatedAt(attendance.getUpdatedAt())
                        .studentId(attendance.getStudent() != null ? attendance.getStudent().getId() : null)
                        .studentName(attendance.getStudent() != null ? attendance.getStudent().getName() : null)
                        .subjectId(attendance.getSubject() != null ? attendance.getSubject().getId() : null)
                        .subjectName(attendance.getSubject() != null ? attendance.getSubject().getName() : null)
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/marks/{studentId}")
    public ResponseEntity<List<MarksResponse>> getStudentMarksInAllSubjects(@PathVariable Long studentId){
        List<Marks> marksList = studentService.studentMarksInAllSubjects(studentId);
        List<MarksResponse> responses = marksList.stream()
                .map(marks -> MarksResponse.builder()
                        .id(marks.getId())
                        .marks(marks.getMarks())
                        .markedById(marks.getMarkedById())
                        .createdAt(marks.getCreatedAt())
                        .updatedAt(marks.getUpdatedAt())
                        .studentId(marks.getStudent() != null ? marks.getStudent().getId() : null)
                        .studentName(marks.getStudent() != null ? marks.getStudent().getName() : null)
                        .subjectId(marks.getSubject() != null ? marks.getSubject().getId() : null)
                        .subjectName(marks.getSubject() != null ? marks.getSubject().getName() : null)
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}
