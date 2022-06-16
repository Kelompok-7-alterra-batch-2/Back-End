package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodTypeRepository extends JpaRepository<BloodTypeEntity, Long> {
}
