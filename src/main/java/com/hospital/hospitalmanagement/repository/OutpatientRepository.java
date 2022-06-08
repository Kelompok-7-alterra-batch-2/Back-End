package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutpatientRepository extends JpaRepository<OutpatientEntity, Long> {
}
