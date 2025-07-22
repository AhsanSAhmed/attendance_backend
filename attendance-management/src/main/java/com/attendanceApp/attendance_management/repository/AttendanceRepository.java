package com.attendanceApp.attendance_management.repository;

import com.attendanceApp.attendance_management.entity.Attendance;
import com.attendanceApp.attendance_management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long>
{
    Optional<Attendance> findByStudentAndDate(Student student, LocalDate date);

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByStudent(Student student);
}
