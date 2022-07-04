package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
public class AdminDTO {
    private String name;
    private String dob;
    @NotEmpty(message = "Password Is Required")
    private String password;
    @NotEmpty(message = "Email Is Required")
    @Email(message = "Invaild Email Format")
    private String email;
    private String phoneNumber;
}
