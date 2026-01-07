package com.example.HospitalManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    private String token;
    private LocalDateTime expiry;
}
