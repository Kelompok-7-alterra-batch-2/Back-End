package com.hospital.hospitalmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "outpatient_condition")
public class OutpatientConditionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;

    @Column(name = "conditions")
    private String conditions;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
