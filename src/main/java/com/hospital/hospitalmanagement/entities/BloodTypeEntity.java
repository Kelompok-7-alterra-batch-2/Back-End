package com.hospital.hospitalmanagement.entities;

import lombok.*;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
}
