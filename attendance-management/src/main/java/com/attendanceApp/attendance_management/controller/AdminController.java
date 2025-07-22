package com.attendanceApp.attendance_management.controller;

import com.attendanceApp.attendance_management.dto.GradeDTO;
import com.attendanceApp.attendance_management.dto.StudentDTO;
import com.attendanceApp.attendance_management.dto.StudentListDTO;
import com.attendanceApp.attendance_management.dto.UserDTO;
import com.attendanceApp.attendance_management.services.AdminService;
import com.attendanceApp.attendance_management.services.GradeService;
import com.attendanceApp.attendance_management.services.StudentService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(origins = "*")
public class AdminController
{
    @Autowired
    private AdminService adminService;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private StudentService studentService;

    @PostMapping("/create-user")
    public ResponseEntity<?> signupUser(@RequestBody UserDTO dto)
    {
        try
        {
            UserDTO createdUser=adminService.createUser(dto);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        }
        catch (EntityExistsException | EntityNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("User not created. Try again later",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/create-grade")
    public ResponseEntity<?> createGrade(@RequestBody GradeDTO dto)
    {
        try
        {
            GradeDTO createdSection=gradeService.createGrade(dto);
            return new ResponseEntity<>(createdSection, HttpStatus.OK);
        }
        catch (EntityExistsException | EntityNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Grade not created. Try again later",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/create-student")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO dto)
    {
        try
        {
            StudentDTO createdSection=studentService.createStudent(dto);
            return new ResponseEntity<>(createdSection, HttpStatus.OK);
        }
        catch (EntityExistsException | EntityNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Student not created. Try again later",HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/create-batch-grade")
    public ResponseEntity<?> createBatchStudents(@RequestBody StudentListDTO studentListDTO)
    {
        try {
            StudentListDTO createdStudents = studentService.createBatchStudent(studentListDTO);
            return new ResponseEntity<>(createdStudents, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("Batch creation failed. Try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
