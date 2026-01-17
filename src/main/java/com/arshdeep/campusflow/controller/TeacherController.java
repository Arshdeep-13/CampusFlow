package com.arshdeep.campusflow.controller;

import com.arshdeep.campusflow.entity.Student;
import com.arshdeep.campusflow.entity.Subject;
import com.arshdeep.campusflow.entity.Teacher;
import com.arshdeep.campusflow.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/{teacherId}/subjects")
    public List<Subject> getSubjectsByTeacherId(@PathVariable Integer teacherId){
        return teacherService.getSubjectsByTeacherId(teacherId);
    }

    @GetMapping("/{teacherId}/subject/{subjectId}/students")
    public List<Student> getStudentsOfSubject(@PathVariable Long subjectId, @PathVariable Long teacherId){
        return teacherService.getStudentsOfSubject(subjectId, teacherId);
    }

    @PostMapping("/{teacherId}/subject/{subjectId}/student/{studentId}/attendance")
    public String markStudentAttendance(@PathVariable Long teacherId, @PathVariable Long subjectId, @PathVariable Long studentId, @RequestParam boolean present){
        return teacherService.markStudentAttendance(teacherId, subjectId, studentId, present);
    }

    @PostMapping("/{teacherId}/subject/{subjectId}/student/{studentId}/marks")
    public String markStudentMarks(@PathVariable Long teacherId, @PathVariable Long subjectId, @PathVariable Long studentId, @RequestBody Integer marks){
        return teacherService.markStudentMarks(teacherId, subjectId, studentId, marks);
    }
}
