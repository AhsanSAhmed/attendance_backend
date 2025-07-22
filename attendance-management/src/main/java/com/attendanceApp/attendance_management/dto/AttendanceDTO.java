package com.attendanceApp.attendance_management.dto;

import com.attendanceApp.attendance_management.entity.Student;
import com.attendanceApp.attendance_management.enums.AttendanceStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceDTO
{
    private Long Id;

    private String studentRoll;

    private String userName;

    private LocalDate date;

    private AttendanceStatus status;

    private String reason;
}
