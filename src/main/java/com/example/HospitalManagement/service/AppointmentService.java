package com.example.HospitalManagement.service;

import com.example.HospitalManagement.entity.Appointment;
import com.example.HospitalManagement.entity.Doctor;
import com.example.HospitalManagement.entity.Patient;
import com.example.HospitalManagement.entity.User;
import com.example.HospitalManagement.repository.AppointmentRepository;
import com.example.HospitalManagement.repository.DoctorRepository;
import com.example.HospitalManagement.repository.PatientRepository;
import com.example.HospitalManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AppointmentService {

    @Autowired
    private DoctorRepository doctorRepo;

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Appointment book(Long doctorId, LocalDateTime time) {

        //  Get logged-in username from JWT
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // Find User
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //JWT only authenticates the request.
        //UserRepository allows us to map the authenticated principal to a persistent user entity,
        // which we then use to securely access role-specific domain models like Patient or Doctor
        // without trusting client-provided IDs.

        // Find Patient linked to User
        Patient patient = patientRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));


        // Lock doctor row
        Doctor doctor = doctorRepo.lockDoctorById(doctorId);

        if (!doctor.isAvailable()) {
            throw new RuntimeException("Doctor not available");
        }

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(time);

        // Mark doctor unavailable
        doctor.setAvailable(false);

        return appointmentRepo.save(appointment);
    }
}
