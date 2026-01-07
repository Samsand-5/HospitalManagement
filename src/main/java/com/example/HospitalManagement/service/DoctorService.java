package com.example.HospitalManagement.service;

import com.example.HospitalManagement.entity.Doctor;
import com.example.HospitalManagement.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public Doctor createDoctor(Doctor doctor) {
        doctor.setAvailable(true);
        return doctorRepository.save(doctor);
    }

    @Transactional(readOnly = true)
    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
    }

    @Transactional(readOnly = true)
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor updateAvailability(Long doctorId, boolean available) {
        Doctor doctor = getDoctorById(doctorId);
        doctor.setAvailable(available);
        return doctor;
    }
}

