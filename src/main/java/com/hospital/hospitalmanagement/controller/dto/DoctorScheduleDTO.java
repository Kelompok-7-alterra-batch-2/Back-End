package com.hospital.hospitalmanagement.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class DoctorScheduleDTO {
    private LocalTime availableFrom;
    private LocalTime availableTo;
}
