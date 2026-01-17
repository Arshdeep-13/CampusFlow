package com.arshdeep.campusflow.repository;

import com.arshdeep.campusflow.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}