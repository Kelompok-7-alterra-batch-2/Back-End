package com.hospital.hospitalmanagement.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GetPatientDTO {
    private Long id;
    private String name;
    private Long medicalRecord;
    private LocalDate dob;
}
