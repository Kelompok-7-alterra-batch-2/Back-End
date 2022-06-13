package com.hospital.hospitalmanagement.controller.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class GetDoctorDTO {
    private Long id;
    private String name;
    private String dob;
    private String email;
    private LocalTime availableFrom;
    private LocalTime availableTo;
}
