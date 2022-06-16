package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.OutpatientConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutpatientConditionRepository extends JpaRepository<OutpatientConditionEntity, Long> {
}
