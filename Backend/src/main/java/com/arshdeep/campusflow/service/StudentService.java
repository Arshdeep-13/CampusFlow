package com.arshdeep.campusflow.service;

import com.arshdeep.campusflow.dto.response.StudentCountResponse;
import com.arshdeep.campusflow.entity.Attendance;
import com.arshdeep.campusflow.entity.Marks;
import com.arshdeep.campusflow.entity.Student;
import com.arshdeep.campusflow.repository.AttendanceRepository;
import com.arshdeep.campusflow.repository.MarksRepository;
import com.arshdeep.campusflow.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final AttendanceRepository attendanceRepository;
    private final MarksRepository marksRepository;
    private final StudentRepository studentRepository;

    public List<Attendance> studentAttendanceInAllSubjects(Long studentId){
        try {
            List<Attendance> studentAttendance = attendanceRepository.findByStudentId(studentId);
            return studentAttendance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Marks> studentMarksInAllSubjects(Long studentId){
        try {
            List<Marks> studentMarks = marksRepository.findByStudentId(studentId);
            return studentMarks;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public StudentCountResponse getStudentCount() {
        try {
            Long totalStudents = studentRepository.count();
            return new StudentCountResponse(totalStudents);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> getAllStudents() {
        try {
            return studentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
