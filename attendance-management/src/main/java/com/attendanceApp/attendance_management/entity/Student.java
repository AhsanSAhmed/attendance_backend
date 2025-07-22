package com.attendanceApp.attendance_management.entity;

import com.attendanceApp.attendance_management.dto.StudentDTO;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Student
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @Column(unique = true)
    private String rollNumber;

    @ManyToOne
    private Grade grade;

    public StudentDTO getStudentDTO()
    {
        StudentDTO dto = new StudentDTO();

        dto.setId(Id);
        dto.setName(name);
        dto.setRollNumber(rollNumber);
        dto.setGradeName(grade.getName());
        dto.setGradeId(grade.getId());

        return dto;
    }
}
