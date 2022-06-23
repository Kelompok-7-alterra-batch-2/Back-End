package com.hospital.hospitalmanagement.controller.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDoctorDTO {
    private Long id;
    private String name;
    private LocalDate dob;
    private String email;
    private LocalTime availableFrom;
    private LocalTime availableTo;
}
