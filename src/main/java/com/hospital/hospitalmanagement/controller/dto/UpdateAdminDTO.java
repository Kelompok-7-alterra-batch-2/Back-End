package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UpdateAdminDTO {
    @NotEmpty(message = "Name Is Required")
    private String name;
    private String dob;
    @NotEmpty(message = "Email Is Required")
    @Email(message = "Invaild Email Format")
    private String email;
    private String phoneNumber;
}
