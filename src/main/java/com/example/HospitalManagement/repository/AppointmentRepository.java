package com.example.HospitalManagement.repository;

import com.example.HospitalManagement.entity.Appointment;
import com.example.HospitalManagement.entity.Doctor;
import com.example.HospitalManagement.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Page<Appointment> findByPatient(Patient patient, Pageable pageable);
    Page<Appointment> findByDoctor(Doctor doctor, Pageable pageable);
}
