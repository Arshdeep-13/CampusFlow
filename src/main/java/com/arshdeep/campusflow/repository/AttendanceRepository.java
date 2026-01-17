package com.arshdeep.campusflow.repository;

import com.arshdeep.campusflow.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}