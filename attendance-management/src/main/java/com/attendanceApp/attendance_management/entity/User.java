package com.attendanceApp.attendance_management.entity;

import com.attendanceApp.attendance_management.dto.UserDTO;
import com.attendanceApp.attendance_management.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String username;

    private String password;

    private UserRole userRole;

    public UserDTO getDTO()
    {
        UserDTO dto=new UserDTO();

        dto.setId(Id);
        dto.setUsername(username);
        dto.setUserRole(userRole);

        return dto;
    }
}
