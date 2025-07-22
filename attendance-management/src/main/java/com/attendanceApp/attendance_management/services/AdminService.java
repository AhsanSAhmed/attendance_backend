package com.attendanceApp.attendance_management.services;

import com.attendanceApp.attendance_management.dto.UserDTO;
import com.attendanceApp.attendance_management.entity.User;
import com.attendanceApp.attendance_management.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO dto)
    {
        boolean exists=userRepository.findByUsername(dto.getUsername()).isPresent();
        if(exists)
        {
            throw new EntityExistsException("User already exists");
        }

        User user=new User();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setUserRole(dto.getUserRole());

        User userCreated=userRepository.save(user);

        return userCreated.getDTO();
    }
}
