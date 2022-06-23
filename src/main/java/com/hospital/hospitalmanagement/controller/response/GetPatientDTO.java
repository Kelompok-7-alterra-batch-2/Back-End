package com.hospital.hospitalmanagement.controller.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPatientDTO {
    private Long id;
    private String name;
    private Long medicalRecord;
    private LocalDate dob;
}
