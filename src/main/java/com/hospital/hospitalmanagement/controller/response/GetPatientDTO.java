package com.hospital.hospitalmanagement.controller.response;

import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import com.hospital.hospitalmanagement.entities.GenderEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPatientDTO {
    private Long id;
    private String name;
    private LocalDate dob;
    private String phoneNumber;
    private String city;
    private String address;
    private BloodTypeEntity bloodType;
    private GenderEntity gender;
    private LocalDateTime createdAt;
}
