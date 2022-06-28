package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class AdminDTO {
    private String name;
    private String dob;
    private String password;
    private String email;
    private String phoneNumber;
}
