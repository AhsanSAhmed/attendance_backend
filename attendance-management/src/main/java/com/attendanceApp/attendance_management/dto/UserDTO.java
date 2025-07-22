package com.attendanceApp.attendance_management.dto;

import com.attendanceApp.attendance_management.enums.UserRole;
import lombok.Data;

@Data
public class UserDTO
{
    private Long Id;

    private String username;

    private String password;

    private UserRole userRole;
}
