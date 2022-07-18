package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UpdateDoctorDTO {
    @NotEmpty(message = "Name Is Required")
    private String name;
    private String dob;
    @Email(message = "Invaild Email Format")
    private String email;
    private Long department_id;
    private String nid;
    private String phoneNumber;
}
