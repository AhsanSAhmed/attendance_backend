package com.attendanceApp.attendance_management.services;

import com.attendanceApp.attendance_management.dto.StudentDTO;
import com.attendanceApp.attendance_management.dto.StudentListDTO;
import com.attendanceApp.attendance_management.entity.Grade;
import com.attendanceApp.attendance_management.entity.Student;
import com.attendanceApp.attendance_management.repository.GradeRepository;
import com.attendanceApp.attendance_management.repository.StudentRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService
{
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO createStudent(StudentDTO dto)
    {
        Optional<Student> existing = studentRepository.findByRollNumber(dto.getRollNumber());
        if (existing.isPresent()) {
            throw new EntityExistsException("Student with this roll number already exists");
        }

        Grade grade = gradeRepository.findByName(dto.getGradeName())
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        Student student = new Student();
        student.setName(dto.getName());
        student.setRollNumber(dto.getRollNumber());
        student.setGrade(grade);

        Student createdStudent=studentRepository.save(student);

        return createdStudent.getStudentDTO();
    }

    public StudentListDTO createBatchStudent(StudentListDTO dtoList)
    {
        List<StudentDTO> studentDTOs = dtoList.getStudents();
        List<Student> studentsToSave = new ArrayList<>();
        List<String> duplicateRolls = new ArrayList<>();

        for (StudentDTO dto : studentDTOs)
        {
            boolean exists = studentRepository.findByRollNumber(dto.getRollNumber()).isPresent();
            if (exists)
            {
                duplicateRolls.add("Roll: " + dto.getRollNumber() + "- Name: " + dto.getName());
                continue;
            }

            // Get grade by name
            Grade grade = gradeRepository.findByName(dto.getGradeName())
                    .orElseThrow(() -> new RuntimeException("Grade not found: " + dto.getGradeName()));

            Student student = new Student();
            student.setName(dto.getName());
            student.setRollNumber(dto.getRollNumber());
            student.setGrade(grade);

            studentsToSave.add(student);
        }

        // Save all valid students
        List<Student> savedStudents = studentRepository.saveAll(studentsToSave);

        // Prepare response DTO list
        List<StudentDTO> savedDTOs = savedStudents.stream()
                .map(Student::getStudentDTO)
                .toList();

        StudentListDTO response = new StudentListDTO();
        response.setStudents(savedDTOs);

        if (!duplicateRolls.isEmpty()) {
            System.out.println("Skipped duplicate roll numbers: " + duplicateRolls);
        }

        return response;
    }

    public void deleteStudent(List<String> rollNumbers) {
        List<Student> students = rollNumbers.stream()
                .map(roll -> studentRepository.findByRollNumber(roll)
                        .orElseThrow(() -> new EntityNotFoundException("Student with roll number " + roll + " not found")))
                .toList();

        studentRepository.deleteAll(students);
    }

    public StudentDTO getStudent(String rollNumber)
    {
        Student student=studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        return student.getStudentDTO();
    }

    public List<StudentDTO> getStudentsByGrade(String gradeName)
    {
        Grade grade = gradeRepository.findByName(gradeName)
                .orElseThrow(() -> new RuntimeException("Grade not found"));

        List<Student> students = studentRepository.findByGrade(grade);
        return students.stream()
                .map(Student::getStudentDTO)
                .toList();
    }

}
