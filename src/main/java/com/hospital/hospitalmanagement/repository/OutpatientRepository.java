package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.OutpatientConditionEntity;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.UserEntity;
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

    List<OutpatientEntity> findAllByOutpatientCondition(OutpatientConditionEntity outpatientCondition);

    List<OutpatientEntity> findAllByDepartmentAndDate(DepartmentEntity department, LocalDate date);

    List<OutpatientEntity> findAllByDoctorAndDate(UserEntity doctor, LocalDate date);

    List<OutpatientEntity> findAllByDoctorOrderByQueueAsc(UserEntity doctor);

    List<OutpatientEntity> findAllByOutpatientConditionAndDateAndDoctorOrderByQueueAsc(OutpatientConditionEntity outpatientCondition, LocalDate date, UserEntity doctor);

    @Modifying
    @Query(
            value = "TRUNCATE TABLE outpatient",
            nativeQuery = true
    )
    void truncateMyTable();

    List<OutpatientEntity> findAllByDoctorAndDateOrderByQueueAsc(UserEntity existDoctor, LocalDate now);
}
