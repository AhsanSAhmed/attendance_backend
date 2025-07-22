package com.attendanceApp.attendance_management.repository;

import com.attendanceApp.attendance_management.entity.Grade;
import com.attendanceApp.attendance_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>
{
    Optional<Student> findByRollNumber(String rollNumber);

    List<Student> findByGrade(Grade grade);
}
