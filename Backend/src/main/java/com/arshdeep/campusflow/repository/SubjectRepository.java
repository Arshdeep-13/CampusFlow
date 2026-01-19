package com.arshdeep.campusflow.repository;

import com.arshdeep.campusflow.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
  List<Subject> findByTeacherId(Integer teacherId);

    Optional<Subject> findByName(String subjectName);
}