package com.hospital.hospitalmanagement.repository;

import com.hospital.hospitalmanagement.entities.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    List<OutpatientEntity> findAllByOutpatientConditionAndDateOrderByQueueAsc(OutpatientConditionEntity outpatientCondition, LocalDate date);

    List<OutpatientEntity> findAllByDepartmentAndDate(DepartmentEntity department, LocalDate date);

    List<OutpatientEntity> findAllByDoctorAndDateOrderByQueueAsc(UserEntity doctor, LocalDate date);

    List<OutpatientEntity> findAllByDoctorOrderByQueueAsc(UserEntity doctor);

    List<OutpatientEntity> findAllByOutpatientConditionAndDateAndDoctorOrderByQueueAsc(OutpatientConditionEntity outpatientCondition, LocalDate date, UserEntity doctor);

    @Modifying
    @Query(
            value = "TRUNCATE TABLE outpatient",
            nativeQuery = true
    )
    void truncateMyTable();

    List<OutpatientEntity> findAllByPatientNameContainsIgnoreCaseAndDate(String name, LocalDate date);

    List<OutpatientEntity> findAllByPatientAndDate(PatientEntity patient, LocalDate date);

    List<OutpatientEntity> findAllByPatient(PatientEntity patient);

    Page<OutpatientEntity> findAllByPaginate(Pageable pageable);
}
