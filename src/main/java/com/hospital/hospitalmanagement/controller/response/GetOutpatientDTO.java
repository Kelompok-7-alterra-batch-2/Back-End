package com.hospital.hospitalmanagement.controller.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hospital.hospitalmanagement.controller.dto.*;
import com.hospital.hospitalmanagement.entities.*;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetOutpatientDTO {
    private Long id;
    private GetPatientDTO patient;
    private GetDoctorDTO doctor;
    private DepartmentEntity department;
    private OutpatientConditionEntity outpatientCondition;
    private String diagnosis;
    private String prescription;
    private String medicalRecord;
    private String appointmentReason;
    private int queue;
    private LocalDate date;
    private LocalTime arrivalTime;
    private LocalDateTime createdAt;
}
