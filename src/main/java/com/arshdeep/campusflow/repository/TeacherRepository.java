package com.arshdeep.campusflow.repository;

import com.arshdeep.campusflow.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}