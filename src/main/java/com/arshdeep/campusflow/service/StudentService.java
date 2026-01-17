package com.arshdeep.campusflow.service;

import com.arshdeep.campusflow.entity.Attendance;
import com.arshdeep.campusflow.entity.Marks;
import com.arshdeep.campusflow.repository.AttendanceRepository;
import com.arshdeep.campusflow.repository.MarksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final AttendanceRepository attendanceRepository;
    private final MarksRepository marksRepository;

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
}
