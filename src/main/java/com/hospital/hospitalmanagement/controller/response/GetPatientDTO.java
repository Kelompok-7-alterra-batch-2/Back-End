package com.hospital.hospitalmanagement.controller.response;

import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import com.hospital.hospitalmanagement.entities.GenderEntity;
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
    private String phoneNumber;
    private String city;
    private String address;
    GetOutpatientDTO outpatient;
    BloodTypeEntity bloodType;
    GenderEntity gender;
}
