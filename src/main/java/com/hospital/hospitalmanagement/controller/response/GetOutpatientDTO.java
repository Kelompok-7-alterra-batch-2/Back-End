package com.hospital.hospitalmanagement.controller.response;

import com.hospital.hospitalmanagement.controller.dto.*;
import com.hospital.hospitalmanagement.entities.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetOutpatientDTO {
    private Long id;
    private GetPatientDTO patient;
    private GetDoctorDTO doctor;
    private DepartmentEntity department;
    private OutpatientConditionEntity outpatientCondition;
    private int queue;
    private String appointmentReason;
    private LocalDate date;
    private LocalTime arrivalTime;
    private String diagnosis;
    private String prescription;
    private LocalDateTime createAt;
}
