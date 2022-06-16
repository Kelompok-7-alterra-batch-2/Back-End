package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PatientDTO {
    private String name;
    private String dob;
    private Long blood_type_id;
    private Long gender_id;
    private String phoneNumber;
    private String city;
    private String address;
}
