package com.arshdeep.campusflow.repository;

import com.arshdeep.campusflow.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
  List<Attendance> findByStudentId(Long studentId);
}