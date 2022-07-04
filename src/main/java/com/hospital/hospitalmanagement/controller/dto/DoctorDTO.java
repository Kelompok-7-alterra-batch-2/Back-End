package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;

@Getter
@Setter
public class DoctorDTO {
    private String name;
    private String dob;
    @NotEmpty(message = "Password Is Required")
    private String password;
    @NotEmpty(message = "Email Is Required")
    @Email(message = "Invaild Email Format")
    private String email;
    private Long department_id;
    private String nid;
    private String phoneNumber;
}
