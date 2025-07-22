package com.attendanceApp.attendance_management.entity;

import com.attendanceApp.attendance_management.dto.GradeDTO;
import jakarta.persistence.*;
import lombok.Data;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Data
public class Grade
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @OneToMany(mappedBy = "grade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    public GradeDTO getGradeDTO()
    {
        GradeDTO dto=new GradeDTO();

        dto.setId(Id);
        dto.setName(name);

        if (students != null && !students.isEmpty())
        {
            List<Long> studentIds = students.stream()
                    .map(Student::getId)
                    .collect(Collectors.toList());
            dto.setStudentIds(studentIds);
        }

        return dto;
    }
}
