package com.example.HospitalManagement.entity;

import com.example.HospitalManagement.entity.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;


    @Column(unique = true)
    private String username;
    private String password;


    @Enumerated(EnumType.STRING)
    private Role role;
}
