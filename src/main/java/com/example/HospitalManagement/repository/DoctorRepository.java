package com.example.HospitalManagement.repository;

import com.example.HospitalManagement.entity.Doctor;
import com.example.HospitalManagement.entity.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM Doctor d WHERE d.id = :id")
    Doctor lockDoctorById(@Param("id") Long id);

    Optional<Doctor> findByUser(User user);

}
