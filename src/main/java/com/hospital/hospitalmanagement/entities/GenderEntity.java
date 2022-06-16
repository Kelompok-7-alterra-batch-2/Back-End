package com.hospital.hospitalmanagement.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "gender")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;

}
