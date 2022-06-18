package com.hospital.hospitalmanagement.controller.response;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.OutpatientEntity;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetDoctorDTO {
    private Long id;
    private String name;
    private String dob;
    private String password;
    private String email;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private String nid;
    private String phoneNumber;
    private DepartmentEntity department;
    private RoleEntity role;
    private LocalDateTime createdAt;
    private List<OutpatientEntity> outpatient;
}
