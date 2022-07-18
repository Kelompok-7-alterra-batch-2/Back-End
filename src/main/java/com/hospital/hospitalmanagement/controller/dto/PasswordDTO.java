package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PasswordDTO {
    @NotEmpty(message = "Password Is Required")
    private String password;
}
