package com.arshdeep.campusflow.repository;

import com.arshdeep.campusflow.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}