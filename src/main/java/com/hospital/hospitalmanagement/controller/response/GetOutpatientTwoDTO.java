package com.hospital.hospitalmanagement.controller.response;

import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.OutpatientConditionEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOutpatientTwoDTO {
    private Long id;
    private GetDoctorDTO doctor;
    private DepartmentEntity department;
    private OutpatientConditionEntity outpatientCondition;
    private int queue;
    private String appointmentReason;
    private String medicalRecord;
    private LocalDate date;
    private LocalTime arrivalTime;
    private String diagnosis;
    private String prescription;
    private LocalDateTime createAt;
}
