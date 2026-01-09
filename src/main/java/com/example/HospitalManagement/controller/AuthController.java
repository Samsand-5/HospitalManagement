package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.enums.Role;
import com.example.HospitalManagement.service.AuthResponse;
import com.example.HospitalManagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public void register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam Role role
    ) {
        authService.register(username, password, role);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestParam String username,
            @RequestParam String password
    ) {
        return authService.login(username, password);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestParam String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
