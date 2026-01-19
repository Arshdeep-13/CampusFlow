package com.arshdeep.campusflow.service;

import com.arshdeep.campusflow.dto.request.AdminCreateRequest;
import com.arshdeep.campusflow.dto.request.CourseCreateRequest;
import com.arshdeep.campusflow.dto.request.StudentCreateRequest;
import com.arshdeep.campusflow.dto.request.StudentEditRequest;
import com.arshdeep.campusflow.dto.request.TeacherCreateRequest;
import com.arshdeep.campusflow.dto.response.CourseCountResponse;
import com.arshdeep.campusflow.dto.response.SubjectCountResponse;
import com.arshdeep.campusflow.entity.Course;
import com.arshdeep.campusflow.entity.Student;
import com.arshdeep.campusflow.entity.Subject;
import com.arshdeep.campusflow.entity.Teacher;
import com.arshdeep.campusflow.entity.User;
import com.arshdeep.campusflow.entity.type.RoleType;
import com.arshdeep.campusflow.repository.CourseRepository;
import com.arshdeep.campusflow.repository.StudentRepository;
import com.arshdeep.campusflow.repository.SubjectRepository;
import com.arshdeep.campusflow.repository.TeacherRepository;
import com.arshdeep.campusflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createStudent(StudentCreateRequest studentCreateRequest) {
        try {
            Optional<User> existingUser = userRepository.findByUsername(studentCreateRequest.getName());
            if (existingUser.isPresent()) {
                return null;
            }

            User user = User.builder()
                    .username(studentCreateRequest.getName())
                    .password(passwordEncoder.encode(studentCreateRequest.getPassword()))
                    .role(RoleType.STUDENT)
                    .build();
            
            user = userRepository.save(user);

            Student student = Student.builder()
                    .name(studentCreateRequest.getName())
                    .user(user)
                    .build();

            studentRepository.save(student);

            return "student created successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public String createTeacher(TeacherCreateRequest teacherCreateRequest) {
        try {
            Optional<Teacher> existingTeacher = teacherRepository.findByName((teacherCreateRequest.getName()));

            if (existingTeacher.isPresent()) {
                return null;
            }

            User user = User.builder()
                    .username(teacherCreateRequest.getName())
                    .password(passwordEncoder.encode(teacherCreateRequest.getPassword()))
                    .role(RoleType.TEACHER)
                    .build();
            
            user = userRepository.save(user);

            Teacher teacher = Teacher.builder()
                    .name(teacherCreateRequest.getName())
                    .user(user)
                    .build();

            teacherRepository.save(teacher);

            return "teacher created successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public String createAdmin(AdminCreateRequest adminCreateRequest) {
        try {
            if (userRepository.findByUsername(adminCreateRequest.getUsername()).isPresent()) {
                return null;
            }

            User user = User.builder()
                    .username(adminCreateRequest.getUsername())
                    .password(passwordEncoder.encode(adminCreateRequest.getPassword()))
                    .role(RoleType.ADMIN)
                    .build();
            
            userRepository.save(user);

            return "admin created successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createSubject(String subjectName) {
        try {
            Optional<Subject> existingSubject = subjectRepository.findByName(subjectName);

            if (existingSubject.isPresent()) {
                return null;
            }

            Subject subject = Subject.builder()
                    .name(subjectName)
                    .build();

            subjectRepository.save(subject);

            return "subject created successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String createCourse(CourseCreateRequest courseCreateRequest) {
        try {
            Optional<Course> existingCourse = courseRepository.findByCourseCode(courseCreateRequest.getCourseCode());

            if (existingCourse.isPresent()) {
                return null;
            }

            Course course = Course.builder()
                    .name(courseCreateRequest.getCourseName())
                    .courseCode(courseCreateRequest.getCourseCode())
                    .build();

            courseRepository.save(course);

            return "course created successfully";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String assignStudentToCourse(Long studentId, Long courseId) {
        Optional<Student> student = studentRepository.findById(studentId);

        if(student.isPresent()) {
            Optional<Course> course = courseRepository.findById(courseId);

            if(course.isPresent()) {
                student.get().setCourse(course.get());

                studentRepository.save(student.get());

                return "student assigned to course successfully";
            }

            return "course not found";
        }

        return "student not found";
    }

    public String assignTeacherToSubjects(Long teacherId, Long[] subjectIds) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);

        if(teacher.isPresent()) {
            for(Long subjectId : subjectIds) {
                Optional<Subject> subject = subjectRepository.findById(subjectId);

                subject.get().setTeacher(teacher.get());

                subjectRepository.save(subject.get());
            }

            return "teacher assigned to subjects successfully";
        }

        return "teacher not found";
    }

    public String assignSubjectsToCourse(Long courseId, Long[] subjectIds) {
        Optional<Course> course = courseRepository.findById(courseId);

        if(course.isPresent()) {
            for(Long subjectId : subjectIds) {
                Optional<Subject> subject = subjectRepository.findById(subjectId);

                if(subject.isPresent()) {
                    subject.get().setCourse(course.get());
                    subjectRepository.save(subject.get());
                }
            }

            courseRepository.save(course.get());

            return "subjects assigned to course successfully";
        }

        return "course not found";
    }

    public String editStudent(StudentEditRequest studentCreateRequest) {
        try {
            Optional<Student> student = studentRepository.findById(studentCreateRequest.getId());

            if(student.isPresent()) {
                student.get().setName(studentCreateRequest.getName());

                studentRepository.save(student.get());

                return "student edited successfully";
            }

            return "student not found";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String editTeacher(TeacherCreateRequest teacherCreateRequest, Long teacherId) {
        try {
            Optional<Teacher> teacher = teacherRepository.findById(teacherId);

            if(teacher.isPresent()) {
                teacher.get().setName(teacherCreateRequest.getName());

                teacherRepository.save(teacher.get());

                return "teacher edited successfully";
            }

            return "teacher not found";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String editSubject(String subjectName, Long subjectId) {
        try {
            Optional<Subject> subject = subjectRepository.findById(subjectId);
            if(subject.isPresent()) {
                subject.get().setName(subjectName);

                subjectRepository.save(subject.get());

                return "subject edited successfully";
            }
            return "subject not found";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String editCourse(CourseCreateRequest courseCreateRequest, Long courseId) {
        try {
            Optional<Course> course = courseRepository.findById(courseId);
            if(course.isPresent()) {
                course.get().setName(courseCreateRequest.getCourseName());
                course.get().setCourseCode(courseCreateRequest.getCourseCode());

                courseRepository.save(course.get());

                return "course edited successfully";
            }
            return "course not found";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String deleteStudent(Long studentId) {
        Optional<Student> student = studentRepository.findById(studentId);

        if (student.isPresent()){
            studentRepository.deleteById(studentId);
            return "student deleted successfully";
        }

        return "student not found";
    }

    public String deleteTeacher(Long teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);

        if (teacher.isPresent()){
            teacherRepository.deleteById(teacherId);
            return "teacher deleted successfully";
        }

        return "teacher not found";
    }

    public String deleteSubject(Long subjectId) {
        Optional<Subject> subject = subjectRepository.findById(subjectId);

        if (subject.isPresent()){
            subjectRepository.deleteById(subjectId);
            return "subject deleted successfully";
        }

        return "subject not found";
    }

    public String deleteCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);

        if (course.isPresent()){
            courseRepository.deleteById(courseId);
            return "course deleted successfully";
        }

        return "course not found";
    }

    public SubjectCountResponse getSubjectCount() {
        try {
            Long count = subjectRepository.count();

            return SubjectCountResponse.builder()
                    .subjectCount(count)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public CourseCountResponse getCourseCount() {
        try {
            Long count = courseRepository.count();

            return CourseCountResponse.builder()
                    .courseCount(count)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Course> getAllCourses() {
        try {
            return courseRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Subject> getAllSubjects() {
        try {
            return subjectRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
