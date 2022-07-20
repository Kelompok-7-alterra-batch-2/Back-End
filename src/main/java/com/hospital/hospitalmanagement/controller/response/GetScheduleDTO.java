package com.hospital.hospitalmanagement.controller.response;

import com.hospital.hospitalmanagement.entities.UserEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetScheduleDTO {
    private Long id;
    private LocalTime availableFrom;
    private LocalTime availableTo;
    private GetDoctorDTO doctor;
    private LocalDateTime createdAt;
}
