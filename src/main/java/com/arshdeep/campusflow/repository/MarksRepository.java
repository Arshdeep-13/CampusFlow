package com.arshdeep.campusflow.repository;

import com.arshdeep.campusflow.entity.Marks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarksRepository extends JpaRepository<Marks, Long> {
}