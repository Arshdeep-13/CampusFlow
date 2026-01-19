package com.arshdeep.campusflow.configuration;

import com.arshdeep.campusflow.dto.response.AttendanceResponse;
import com.arshdeep.campusflow.dto.response.MarksResponse;
import com.arshdeep.campusflow.dto.response.StudentResponse;
import com.arshdeep.campusflow.dto.response.SubjectResponse;
import com.arshdeep.campusflow.entity.Attendance;
import com.arshdeep.campusflow.entity.Marks;
import com.arshdeep.campusflow.entity.Student;
import com.arshdeep.campusflow.entity.Subject;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        // Configure Attendance to AttendanceResponse mapping
        modelMapper.addMappings(new PropertyMap<Attendance, AttendanceResponse>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setPresent(source.isPresent());
                map().setMarkedById(source.getMarkedById());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedAt(source.getUpdatedAt());
                map().setStudentId(source.getStudent() != null ? source.getStudent().getId() : null);
                map().setStudentName(source.getStudent() != null ? source.getStudent().getName() : null);
                map().setSubjectId(source.getSubject() != null ? source.getSubject().getId() : null);
                map().setSubjectName(source.getSubject() != null ? source.getSubject().getName() : null);
            }
        });

        // Configure Marks to MarksResponse mapping
        modelMapper.addMappings(new PropertyMap<Marks, MarksResponse>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setMarks(source.getMarks());
                map().setMarkedById(source.getMarkedById());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedAt(source.getUpdatedAt());
                map().setStudentId(source.getStudent() != null ? source.getStudent().getId() : null);
                map().setStudentName(source.getStudent() != null ? source.getStudent().getName() : null);
                map().setSubjectId(source.getSubject() != null ? source.getSubject().getId() : null);
                map().setSubjectName(source.getSubject() != null ? source.getSubject().getName() : null);
            }
        });

        // Configure Student to StudentResponse mapping
        modelMapper.addMappings(new PropertyMap<Student, StudentResponse>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedAt(source.getUpdatedAt());
                map().setCourseId(source.getCourse() != null ? source.getCourse().getId() : null);
                map().setCourseName(source.getCourse() != null ? source.getCourse().getName() : null);
                map().setCourseCode(source.getCourse() != null ? source.getCourse().getCourseCode() : null);
            }
        });

        // Configure Subject to SubjectResponse mapping
        modelMapper.addMappings(new PropertyMap<Subject, SubjectResponse>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedAt(source.getUpdatedAt());
                map().setCourseId(source.getCourse() != null ? source.getCourse().getId() : null);
                map().setCourseName(source.getCourse() != null ? source.getCourse().getName() : null);
                map().setCourseCode(source.getCourse() != null ? source.getCourse().getCourseCode() : null);
                map().setTeacherId(source.getTeacher() != null ? source.getTeacher().getId() : null);
                map().setTeacherName(source.getTeacher() != null ? source.getTeacher().getName() : null);
                skip(destination.getStudents()); // Skip students field - we'll set it manually in the controller
            }
        });

        return modelMapper;
    }
}
