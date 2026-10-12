package com.parking.system.service;

import com.parking.system.dto.AuthResponse;
import com.parking.system.dto.LoginRequest;
import com.parking.system.dto.RegisterRequest;
import com.parking.system.exception.BadRequestException;
import com.parking.system.exception.ResourceNotFoundException;
import com.parking.system.model.Role;
import com.parking.system.model.User;
import com.parking.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse registerUser(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already registered!");
        }

        Role role = request.getRole() != null ? request.getRole() : Role.USER;

        User user = new User(
                request.getFullName(),
                request.getEmail(),
                request.getPassword(), // Standard plain text or BCrypt for simplicity
                request.getPhone(),
                role
        );

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getRole(),
                "User registered successfully!"
        );
    }

    public AuthResponse loginUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid email or password"));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new BadRequestException("Invalid email or password");
        }

        return new AuthResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                "Login successful!"
        );
    }
}
