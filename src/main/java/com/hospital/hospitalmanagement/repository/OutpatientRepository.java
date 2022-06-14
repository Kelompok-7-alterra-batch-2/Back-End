package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OutpatientRepository extends JpaRepository<OutpatientEntity, Long> {
    List<OutpatientEntity> findByDate(LocalDate date);
}
