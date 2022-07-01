package com.hospital.hospitalmanagement.controller.payload;

import lombok.Data;

@Data
public class Login {
    private String email;
    private String password;
}
