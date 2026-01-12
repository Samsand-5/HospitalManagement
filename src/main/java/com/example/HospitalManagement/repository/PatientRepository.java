package com.example.HospitalManagement.repository;

import com.example.HospitalManagement.entity.Patient;
import com.example.HospitalManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUser(User user);
}