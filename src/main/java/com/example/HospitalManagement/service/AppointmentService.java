package com.example.HospitalManagement.service;

import com.example.HospitalManagement.entity.Appointment;
import com.example.HospitalManagement.entity.Doctor;
import com.example.HospitalManagement.repository.AppointmentRepository;
import com.example.HospitalManagement.repository.DoctorRepository;
import jakarta.validation.constraints.Future;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AppointmentService {

    @Autowired
    private DoctorRepository doctorRepo;
    @Autowired private AppointmentRepository appointmentRepo;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Appointment book(Long doctorId, LocalDateTime time) {

        Doctor doctor = doctorRepo.lockDoctorById(doctorId);

        if (!doctor.isAvailable()) {
            throw new RuntimeException("Doctor not available");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(time);

        doctor.setAvailable(false);

        return appointmentRepo.save(appointment);
    }

}
