package com.attendanceApp.attendance_management.entity;

import com.attendanceApp.attendance_management.dto.AttendanceDTO;
import com.attendanceApp.attendance_management.enums.AttendanceStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Attendance
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private User user;

    private LocalDate date;

    private AttendanceStatus status;

    private String reason;

    public AttendanceDTO getAttendanceDTO()
    {
        AttendanceDTO dto=new AttendanceDTO();

        dto.setId(Id);
        dto.setDate(date);
        dto.setStatus(status);
        dto.setReason(reason);

        dto.setStudentRoll(student.getRollNumber());
        dto.setUserName(user.getUsername());

        return dto;
    }
}
