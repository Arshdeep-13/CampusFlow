package com.arshdeep.campusflow.service;

import com.arshdeep.campusflow.entity.*;
import com.arshdeep.campusflow.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<Subject> getSubjectsByTeacherId(Integer teacherId) {
        try {
            List<Subject> teacherSubjects = subjectRepository.findByTeacherId(teacherId);
            return teacherSubjects;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Student> getStudentsOfSubject(Long subjectId, Long teacherId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);

        if( subject.isPresent() && subject.get().getTeacher().equals(teacherId) ){
            return subject.get().getCourse().getStudents();
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
}
