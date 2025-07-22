package com.attendanceApp.attendance_management.services;

import com.attendanceApp.attendance_management.dto.GradeDTO;
import com.attendanceApp.attendance_management.entity.Grade;
import com.attendanceApp.attendance_management.repository.GradeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class GradeService
{
    @Autowired
    private GradeRepository gradeRepository;

    public GradeDTO createGrade(GradeDTO gradeDTO)
    {
        Optional<Grade> existing = gradeRepository.findByName(gradeDTO.getName());
        if (existing.isPresent()) {
            throw new EntityExistsException("Grade with this name already exists");
        }

        Grade grade=new Grade();

        grade.setName(gradeDTO.getName());

        Grade createdGrade = gradeRepository.save(grade);

        return createdGrade.getGradeDTO();
    }

    public List<GradeDTO> listGrades()
    {
        List<Grade> grades=gradeRepository.findAll();

        return grades.stream()
                .map(Grade::getGradeDTO)
                .toList();
    }

    public void deleteGrade(String name)
    {
        Grade grade = gradeRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found"));

        gradeRepository.delete(grade);
    }

}
