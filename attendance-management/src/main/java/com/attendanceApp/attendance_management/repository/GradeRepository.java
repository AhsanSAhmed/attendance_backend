package com.attendanceApp.attendance_management.repository;

import com.attendanceApp.attendance_management.entity.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long>
{
    Optional<Grade> findByName(String name);
}
