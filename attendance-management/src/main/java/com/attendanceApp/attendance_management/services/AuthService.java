package com.attendanceApp.attendance_management.services;

import com.attendanceApp.attendance_management.dto.UserDTO;
import com.attendanceApp.attendance_management.entity.User;
import com.attendanceApp.attendance_management.enums.UserRole;
import com.attendanceApp.attendance_management.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void createAdminUser()
    {
        List<User> optionalUser=userRepository.findAllByUserRole(UserRole.ADMIN);

        if(optionalUser.isEmpty())
        {
            User user = new User();

            user.setUsername("Admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setUserRole(UserRole.ADMIN);

            userRepository.save(user);
            System.out.println("Admin user created");
        }
        else
        {
            System.out.println("Admin user exists");
        }
    }

    public UserDTO login(UserDTO user)
    {
        Optional<User> dbUser=userRepository.findByUsername(user.getUsername());

        if(dbUser.isPresent() && passwordEncoder.matches(user.getPassword(), dbUser.get().getPassword()))
        {
            return dbUser.get().getDTO();
        }
        return null;
    }
}
