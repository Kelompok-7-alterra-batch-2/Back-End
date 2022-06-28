package com.hospital.hospitalmanagement.entities;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "blood_type")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BloodTypeEntity {
    @Id
    @Column(nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
}
