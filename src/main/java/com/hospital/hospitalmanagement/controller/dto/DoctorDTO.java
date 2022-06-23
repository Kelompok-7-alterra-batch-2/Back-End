package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class DoctorDTO {
    private String name;
    private String dob;
    private String password;
    private String email;
    private Long department_id;
    private String nid;
    private String phoneNumber;
}
