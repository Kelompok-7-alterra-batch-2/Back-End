package com.hospital.hospitalmanagement.controller.response;

import com.hospital.hospitalmanagement.entities.DepartmentEntity;
import com.hospital.hospitalmanagement.entities.RoleEntity;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetDoctorTwoDTO {
    private Long id;
    private String name;
    private LocalDate dob;
    private String email;
    private DepartmentEntity department;
    private RoleEntity role;
    private String nid;
    private String phoneNumber;
    private List<GetOutpatientThreeDTO> outpatient;
    private LocalDateTime createdAt;
}
