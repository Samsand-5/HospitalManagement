package com.example.HospitalManagement.entity.records;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequest(

        @NotNull
        Long doctorId,

        @Future
        LocalDateTime appointmentTime
) {}
