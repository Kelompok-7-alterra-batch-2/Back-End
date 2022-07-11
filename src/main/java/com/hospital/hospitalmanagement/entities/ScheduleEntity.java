package com.hospital.hospitalmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @OneToOne()
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private UserEntity doctor_id;

    @Column(name = "available_from")
    private LocalTime availableFrom;
    @Column(name = "available_to")
    private LocalTime availableTo;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
