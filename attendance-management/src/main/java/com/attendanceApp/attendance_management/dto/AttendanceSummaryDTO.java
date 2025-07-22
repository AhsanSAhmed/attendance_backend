package com.attendanceApp.attendance_management.dto;

import lombok.Data;

@Data
public class AttendanceSummaryDTO {
    private String studentRoll;
    private String studentName;
    private Long presentCount;
    private Long absentCount;
    private Double attendancePercentage;
}
