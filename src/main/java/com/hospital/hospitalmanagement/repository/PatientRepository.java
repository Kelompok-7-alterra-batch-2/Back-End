package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    List<PatientEntity> findByNameContains(String name);
}
