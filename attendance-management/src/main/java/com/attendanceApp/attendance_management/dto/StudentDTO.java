package com.attendanceApp.attendance_management.dto;

import lombok.Data;

@Data
public class StudentDTO
{
    private Long Id;

    private String name;

    private String rollNumber;

    private String gradeName;

    private Long gradeId;
}
