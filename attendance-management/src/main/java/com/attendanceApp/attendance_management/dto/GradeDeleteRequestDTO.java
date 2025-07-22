package com.attendanceApp.attendance_management.dto;

import lombok.Data;

@Data
public class GradeDeleteRequestDTO
{
    private String name;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}

