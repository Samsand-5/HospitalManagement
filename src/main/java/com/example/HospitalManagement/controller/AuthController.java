package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.dto.LoginRequest;
import com.example.HospitalManagement.dto.RegisterRequest;
import com.example.HospitalManagement.service.AuthService;
import com.example.HospitalManagement.service.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ---------------- REGISTER ----------------
    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {

        authService.register(
                request.getUsername(),
                request.getPassword(),
                request.getRole()
        );

        return "User registered successfully";
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        return authService.login(
                request.getUsername(),
                request.getPassword()
        );
    }

    // ---------------- REFRESH TOKEN ----------------
    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestParam String refreshToken) {

        return authService.refresh(refreshToken);
    }
}
