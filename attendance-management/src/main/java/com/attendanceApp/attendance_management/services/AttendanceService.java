package com.attendanceApp.attendance_management.services;

import com.attendanceApp.attendance_management.dto.AttendanceBatchDTO;
import com.attendanceApp.attendance_management.dto.AttendanceDTO;
import com.attendanceApp.attendance_management.dto.AttendanceSummaryDTO;
import com.attendanceApp.attendance_management.entity.Attendance;
import com.attendanceApp.attendance_management.entity.Grade;
import com.attendanceApp.attendance_management.entity.Student;
import com.attendanceApp.attendance_management.entity.User;
import com.attendanceApp.attendance_management.enums.AttendanceStatus;
import com.attendanceApp.attendance_management.repository.AttendanceRepository;
import com.attendanceApp.attendance_management.repository.GradeRepository;
import com.attendanceApp.attendance_management.repository.StudentRepository;
import com.attendanceApp.attendance_management.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class AttendanceService
{
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeRepository gradeRepository;

    public AttendanceDTO markAttendance(AttendanceDTO attendance)
    {
        Student student=studentRepository.findByRollNumber(attendance.getStudentRoll())
                .orElseThrow(()-> new EntityNotFoundException("Student doesn't exist"));

        Optional<Attendance> existing = attendanceRepository.findByStudentAndDate(student, attendance.getDate());

        if(existing.isPresent())
        {
            throw new EntityExistsException("Attendance Record already exists");
        }

        User user=userRepository.findByUsername(attendance.getUserName())
                .orElseThrow(()-> new EntityNotFoundException("User doesn't exist"));

        Attendance record = new Attendance();
        record.setStudent(student);
        record.setUser(user);
        record.setDate(attendance.getDate());
        record.setStatus(attendance.getStatus());
        record.setReason(attendance.getReason());

        Attendance saved = attendanceRepository.save(record);

        return saved.getAttendanceDTO();
    }

    public List<AttendanceDTO> markBatchAttendance(AttendanceBatchDTO batchDTO)
    {
        User user = userRepository.findByUsername(batchDTO.getUserName())
                .orElseThrow(()->new EntityNotFoundException("User doesn't exist"));

        Grade grade = gradeRepository.findByName(batchDTO.getGradeName())
                .orElseThrow(() -> new EntityNotFoundException("Grade not found"));

        List<Student> students=studentRepository.findByGrade(grade);
        if (students.isEmpty()) {
            throw new EntityNotFoundException("No students found in grade: " + batchDTO.getGradeName());
        }

        List<AttendanceDTO> marked = new ArrayList<>();

        for(Student student:students)
        {
            Optional<Attendance> existing=attendanceRepository.findByStudentAndDate(student,batchDTO.getDate());

            if(existing.isEmpty())
            {
                Attendance attendance = new Attendance();
                attendance.setStudent(student);
                attendance.setUser(user);
                attendance.setDate(batchDTO.getDate());
                attendance.setStatus(batchDTO.getStatus());
                attendance.setReason(batchDTO.getReason());

                Attendance saved = attendanceRepository.save(attendance);
                marked.add(saved.getAttendanceDTO());
            }

        }

        return marked;
    }

    public List<AttendanceDTO> getRecordsByDate(LocalDate date)
    {
        List<Attendance> records=attendanceRepository.findByDate(date);

        return records.stream()
                .map(Attendance::getAttendanceDTO)
                .toList();
    }

    public List<AttendanceDTO> getRecordsByStudent(String rollNumber)
    {
        Student student=studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(()->new EntityNotFoundException("Student doesn't exist"));

        List<Attendance> records=attendanceRepository.findByStudent(student);

        return records.stream()
                .map(Attendance::getAttendanceDTO)
                .toList();
    }

    public List<AttendanceSummaryDTO> getSummaryByGrade(String gradeName) {
        Grade grade = gradeRepository.findByName(gradeName)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found"));

        List<Student> students = studentRepository.findByGrade(grade);

        List<AttendanceSummaryDTO> summaries = new ArrayList<>();

        for (Student student : students)
        {
            List<Attendance> records = attendanceRepository.findByStudent(student);

            long present = records.stream().filter(r -> r.getStatus() == AttendanceStatus.PRESENT).count();
            long absent = records.stream().filter(r -> r.getStatus() == AttendanceStatus.ABSENT).count();

            AttendanceSummaryDTO dto = new AttendanceSummaryDTO();
            dto.setStudentRoll(student.getRollNumber());
            dto.setStudentName(student.getName());
            dto.setPresentCount(present);
            dto.setAbsentCount(absent);
            dto.setAttendancePercentage((present+absent) == 0 ? 0.0 : (present * 100.0) / (present+absent));

            summaries.add(dto);
        }

        return summaries;
    }

}
