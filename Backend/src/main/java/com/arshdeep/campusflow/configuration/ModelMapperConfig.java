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
                map().setStudentId(source.getStudent().getId());
                map().setStudentName(source.getStudent().getName());
                map().setStudentRollNumber(source.getStudent().getRollNumber());
                map().setSubjectId(source.getSubject().getId());
                map().setSubjectName(source.getSubject().getName());
            }
        });

        // Configure Marks to MarksResponse mapping
        modelMapper.addMappings(new PropertyMap<Marks, MarksResponse>() {
            @Override
            protected void configure() {
                map().setStudentId(source.getStudent().getId());
                map().setStudentName(source.getStudent().getName());
                map().setStudentRollNumber(source.getStudent().getRollNumber());
                map().setSubjectId(source.getSubject().getId());
                map().setSubjectName(source.getSubject().getName());
            }
        });

        // Configure Student to StudentResponse mapping
        modelMapper.addMappings(new PropertyMap<Student, StudentResponse>() {
            @Override
            protected void configure() {
                map().setCourseId(source.getCourse() != null ? source.getCourse().getId() : null);
                map().setCourseName(source.getCourse() != null ? source.getCourse().getName() : null);
                map().setCourseCode(source.getCourse() != null ? source.getCourse().getCourseCode() : null);
            }
        });

        // Configure Subject to SubjectResponse mapping
        modelMapper.addMappings(new PropertyMap<Subject, SubjectResponse>() {
            @Override
            protected void configure() {
                map().setCourseId(source.getCourse() != null ? source.getCourse().getId() : null);
                map().setCourseName(source.getCourse() != null ? source.getCourse().getName() : null);
                map().setCourseCode(source.getCourse() != null ? source.getCourse().getCourseCode() : null);
                map().setTeacherId(source.getTeacher() != null ? source.getTeacher().getId() : null);
                map().setTeacherName(source.getTeacher() != null ? source.getTeacher().getName() : null);
                map().setTeacherEmployeeId(source.getTeacher() != null ? source.getTeacher().getEmployeeId() : null);
            }
        });

        return modelMapper;
    }
}
