package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.entity.Patient;
import com.example.HospitalManagement.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    // PATIENT: Register profile
    @PostMapping("/addPatient")
    @PreAuthorize("hasRole('PATIENT')")
    public Patient register(@Valid @RequestBody Patient patient) {
        return patientService.registerPatient(patient);
    }

    // ADMIN: Get all patients
    @GetMapping("/getAllPatients")
    @PreAuthorize("hasRole('ADMIN','DOCTOR')")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    // ADMIN / PATIENT: Get patient by ID
    @GetMapping("/getPatient/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PATIENT')")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }
}
