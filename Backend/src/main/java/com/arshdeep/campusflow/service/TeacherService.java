package com.arshdeep.campusflow.service;

import com.arshdeep.campusflow.dto.response.TeacherCountResponse;
import com.arshdeep.campusflow.entity.*;
import com.arshdeep.campusflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherService {
    private final SubjectRepository subjectRepository;
    private final AttendanceRepository attendanceRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final MarksRepository marksRepository;

    @Transactional(readOnly = true)
    public List<Subject> getSubjectsByTeacherId(Integer teacherId) {
        try {
            List<Subject> teacherSubjects = subjectRepository.findByTeacherId(teacherId);
            // Eagerly load course and students to avoid lazy loading issues
            teacherSubjects.forEach(subject -> {
                if (subject.getCourse() != null && subject.getCourse().getStudents() != null) {
                    // Force loading of students by accessing the list
                    subject.getCourse().getStudents().size();
                }
            });
            return teacherSubjects;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Student> getStudentsOfSubject(Long subjectId, Long teacherId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);

        if( subject.isPresent() && subject.get().getTeacher() != null && subject.get().getTeacher().getId().equals(teacherId) ){
            // Ensure course and students are loaded
            if (subject.get().getCourse() != null && subject.get().getCourse().getStudents() != null) {
                // Force loading of students
                subject.get().getCourse().getStudents().size();
                return subject.get().getCourse().getStudents();
            }
        }

        return List.of();
    }

    public String markStudentAttendance(Long teacherId, Long subjectId, Long studentId, boolean present) {
        try {
            Optional<Student> student = studentRepository.findById(studentId);
            Optional<Subject> subject = subjectRepository.findById(subjectId);

            if( student.isEmpty() ){
                return "Student not found";
            }

            if( subject.isEmpty() ){
                return "Subject not found";
            }

            Attendance attendanceObj = Attendance.builder()
                    .student(student.get())
                    .subject(subject.get())
                    .markedById(teacherId)
                    .present(present)
                    .build();

            attendanceRepository.save(attendanceObj);

            return "Attendance marked successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String markStudentMarks(Long teacherId, Long subjectId, Long studentId, Integer marks) {
        try {
            Optional<Student> student = studentRepository.findById(studentId);
            Optional<Subject> subject = subjectRepository.findById(subjectId);

            if( student.isEmpty() ){
                return "Student not found";
            }

            if( subject.isEmpty() ){
                return "Subject not found";
            }

            Marks marksObj = Marks.builder()
                    .student(student.get())
                    .subject(subject.get())
                    .markedById(teacherId)
                    .marks(marks)
                    .build();

            marksRepository.save(marksObj);

            return "Marks added successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public TeacherCountResponse getTeacherCount() {
        try {
            Long count = teacherRepository.count();
            return TeacherCountResponse.builder()
                    .teacherCount(count)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Teacher> getAllTeachers() {
        try {
            List<Teacher> teachers = teacherRepository.findAll();
            return teachers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
