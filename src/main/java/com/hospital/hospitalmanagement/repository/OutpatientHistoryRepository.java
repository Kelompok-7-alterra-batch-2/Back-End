package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.OutpatientHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutpatientHistoryRepository extends JpaRepository<OutpatientHistoryEntity, Long> {
}
