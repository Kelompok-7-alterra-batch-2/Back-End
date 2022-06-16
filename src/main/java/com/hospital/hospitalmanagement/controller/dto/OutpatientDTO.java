package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class OutpatientDTO {
    private Long patient_id;
    private Long doctor_id;
    private Long department_id;
    private Long outpatientCondition_id;
    private int queue;
    private LocalTime arrivalTime;
    private String appointmentReason;
    private String medicalRecord;
    private LocalDate date;
}
