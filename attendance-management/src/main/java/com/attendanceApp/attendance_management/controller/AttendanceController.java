package com.attendanceApp.attendance_management.controller;

import com.attendanceApp.attendance_management.dto.AttendanceBatchDTO;
import com.attendanceApp.attendance_management.dto.AttendanceDTO;
import com.attendanceApp.attendance_management.dto.AttendanceSummaryDTO;
import com.attendanceApp.attendance_management.services.AttendanceService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("api/attendance")
@CrossOrigin("*")
public class AttendanceController
{
    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/mark")
    public ResponseEntity<?> postAttendance(@RequestBody AttendanceDTO attendanceDTO)
    {
        try
        {
            AttendanceDTO record=attendanceService.markAttendance(attendanceDTO);
            return new ResponseEntity<>(record, HttpStatus.OK);
        } catch(EntityExistsException | EntityNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            return  new ResponseEntity<>("Try Later", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/mark-batch")
    public ResponseEntity<?> postAttendance(@RequestBody AttendanceBatchDTO batchDTO)
    {
        try
        {
            List<AttendanceDTO> records=attendanceService.markBatchAttendance(batchDTO);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch(EntityExistsException | EntityNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            return  new ResponseEntity<>("Try Later", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/date")
    public ResponseEntity<?> listByDate(@RequestParam String date)
    {
        try
        {
            LocalDate parsedDate = LocalDate.parse(date);
            List<AttendanceDTO> records=attendanceService.getRecordsByDate(parsedDate);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch(EntityExistsException | EntityNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            return  new ResponseEntity<>("Try Later", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/student")
    public ResponseEntity<?> listByStudent(@RequestParam String rollNumber)
    {
        try
        {
            List<AttendanceDTO> records=attendanceService.getRecordsByStudent(rollNumber);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } catch(EntityExistsException | EntityNotFoundException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e) {
            return  new ResponseEntity<>("Try Later", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/summary/grade/{gradeName}")
    public ResponseEntity<?> getAttendanceSummary(@PathVariable String gradeName) {
        try {
            List<AttendanceSummaryDTO> summary = attendanceService.getSummaryByGrade(gradeName);
            return new ResponseEntity<>(summary, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return  new ResponseEntity<>("Try Later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
