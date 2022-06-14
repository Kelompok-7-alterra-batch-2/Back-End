package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OutpatientDTO {
    private String name;
    private Long patient_id;
    private Long doctor_id;
    private Long department_id;
    private Long outpatientCondition_id;
    private Long queue_id;
}
