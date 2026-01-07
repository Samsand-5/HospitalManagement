package com.example.HospitalManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Patient {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    private String name;

    @Past
    private LocalDate dob;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
}
