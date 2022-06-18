package com.hospital.hospitalmanagement.controller.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hospital.hospitalmanagement.entities.BloodTypeEntity;
import com.hospital.hospitalmanagement.entities.GenderEntity;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetPatientDTO {
    private Long id;
    private String name;
    private LocalDate dob;
    private LocalDateTime createdAt;
    private String phoneNumber;
    private String city;
    private String address;
    private List<OutpatientEntity> outpatient;
    private BloodTypeEntity bloodType;
    private GenderEntity gender;
}
