package com.attendanceApp.attendance_management.repository;

import com.attendanceApp.attendance_management.entity.User;
import com.attendanceApp.attendance_management.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    List<User> findAllByUserRole(UserRole userRole);

    Optional<User> findByUsername(String username);
}
