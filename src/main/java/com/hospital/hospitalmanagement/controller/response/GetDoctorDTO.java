package com.hospital.hospitalmanagement.controller.response;

import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.RoleEntity;
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
    DepartmentEntity departmentEntity;
    RoleEntity roleEntity;
}
