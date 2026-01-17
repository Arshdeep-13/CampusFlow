package com.arshdeep.campusflow.repository;

import com.arshdeep.campusflow.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}