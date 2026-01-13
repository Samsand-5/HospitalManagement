package com.example.HospitalManagement.controller;

import com.example.HospitalManagement.entity.Appointment;
import com.example.HospitalManagement.service.AppointmentRequest;
import com.example.HospitalManagement.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService service;

    @PostMapping("/requestForAppointment")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    public Appointment book(@Valid @RequestBody AppointmentRequest request) {
        return service.book(request.doctorId(), request.appointmentTime());
    }

    @PutMapping("/cancel/{appointmentId}")
    @PreAuthorize("hasAnyRole('PATIENT','DOCTOR')")
    public Appointment cancel(@PathVariable Long appointmentId) {
        return service.cancelAppointment(appointmentId);
    }

}
