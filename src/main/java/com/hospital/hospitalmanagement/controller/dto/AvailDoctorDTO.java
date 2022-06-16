package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class AvailDoctorDTO {
    private LocalTime arrival_time;
    private Long department_id;
}
