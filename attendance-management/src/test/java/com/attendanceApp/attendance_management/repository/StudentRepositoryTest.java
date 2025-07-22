package com.attendanceApp.attendance_management.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @Test
    public void testSaveAndFindStudentByClassRoom() {
        // Setup grade and class
        Grade grade = new Grade();
        grade.setName("Grade 1");
        grade = gradeRepository.save(grade);

        ClassRoom classRoom = new ClassRoom();
        classRoom.setName("A");
        classRoom.setGrade(grade);
        classRoom = classRoomRepository.save(classRoom);

        // Create student
        Student student = new Student();
        student.setName("Alice");
        student.setRollNo("1");
        student.setClassRoom(classRoom);
        studentRepository.save(student);

        // Test custom query
        List<Student> students = studentRepository.findByClassRoom_Id(classRoom.getId());
        Assertions.assertEquals(1, students.size());
        Assertions.assertEquals("Alice", students.get(0).getName());
    }
}