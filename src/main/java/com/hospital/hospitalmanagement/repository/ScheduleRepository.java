package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findAllByAvailableFromLessThanAndAvailableToGreaterThanAndDoctorDepartment(LocalTime arrivalTime1, LocalTime arrivalTime2, DepartmentEntity department);

    List<ScheduleEntity> findByDoctorDepartment(DepartmentEntity department);

    List<ScheduleEntity> findByDoctorNameContainsIgnoreCase(String doctor);
}
