package com.attendanceApp.attendance_management.dto;

import lombok.Data;
import java.util.*;

@Data
public class GradeDTO
{
    private Long Id;

    private String name;

    private List<Long> studentIds;

}
