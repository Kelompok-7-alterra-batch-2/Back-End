package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OutpatientRepository extends JpaRepository<OutpatientEntity, Long> {
    List<OutpatientEntity> findAllByDate(LocalDate date);

    Long countByDate(LocalDate date);
}
