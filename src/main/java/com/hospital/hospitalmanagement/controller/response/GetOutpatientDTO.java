package com.hospital.hospitalmanagement.controller.response;

import com.hospital.hospitalmanagement.controller.dto.*;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.OutpatientConditionEntity;
import com.hospital.hospitalmanagement.entities.PatientEntity;
import com.hospital.hospitalmanagement.entities.QueueEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
public class GetOutpatientDTO {
    private Long id;
    private String name;
    PatientEntity patient;
    GetDoctorDTO doctor;
    DepartmentEntity department;
    OutpatientConditionEntity outpatientCondition;
    int queue;
    private LocalDate date;
    private LocalTime arrivalTime;
    private LocalDateTime createAt;
}
