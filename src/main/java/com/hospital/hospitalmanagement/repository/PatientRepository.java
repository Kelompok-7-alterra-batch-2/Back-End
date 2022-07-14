package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long> {
    List<PatientEntity> findByNameContainsIgnoreCase(String name);

//    @Modifying
//    @Query(
//            value = "TRUNCATE TABLE queue",
//            nativeQuery = true
//    )
//    void resetQueueNative();
}
