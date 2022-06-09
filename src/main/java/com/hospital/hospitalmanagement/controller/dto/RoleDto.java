package com.hospital.hospitalmanagement.controller.dto;

import com.hospital.hospitalmanagement.entities.UserEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Getter
@Setter
public class RoleDto {
    private String name;
}
