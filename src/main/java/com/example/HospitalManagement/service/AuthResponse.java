package com.example.HospitalManagement.service;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
