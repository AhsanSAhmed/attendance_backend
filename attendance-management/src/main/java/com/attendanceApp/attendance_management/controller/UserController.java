package com.attendanceApp.attendance_management.controller;

import com.attendanceApp.attendance_management.dto.GradeDTO;
import com.attendanceApp.attendance_management.dto.GradeDeleteRequestDTO;
import com.attendanceApp.attendance_management.dto.StudentDTO;
import com.attendanceApp.attendance_management.services.GradeService;
import com.attendanceApp.attendance_management.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/user")
@CrossOrigin("*")
public class UserController
{
    @Autowired
    private GradeService gradeService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/grades")
    public ResponseEntity<?> getGrades()
    {
        try
        {
            List<GradeDTO> grades = gradeService.listGrades();
            return new ResponseEntity<>(grades, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not fetch grades", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-grade")
    public ResponseEntity<?> deleteGrade(@RequestBody GradeDeleteRequestDTO request)
    {
        try {
            gradeService.deleteGrade(request.getName());
            return new ResponseEntity<>("Grade deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting grade", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete-students")
    public ResponseEntity<?> deleteStudent(@RequestBody List<String> rollNumbers)
    {
        try {
            studentService.deleteStudent(rollNumbers);
            return new ResponseEntity<>("Students deleted successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error deleting students", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-student")
    public ResponseEntity<?> getStudent(@RequestParam String rollNumber)
    {
        try
        {
            StudentDTO student = studentService.getStudent(rollNumber);
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Could not fetch grades", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get-students-by-grade")
    public ResponseEntity<?> getStudentsByGrade(@RequestParam String gradeName)
    {
        try
        {
            List<StudentDTO> students = studentService.getStudentsByGrade(gradeName);
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
