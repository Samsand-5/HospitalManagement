package com.example.HospitalManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data                   // generates getters/setters/toString
@NoArgsConstructor      // generates default constructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    private String name;

    @Past
    private LocalDate dob;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;
}