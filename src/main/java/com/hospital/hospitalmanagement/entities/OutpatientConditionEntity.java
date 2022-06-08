package com.hospital.hospitalmanagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "outpatient_condition")
public class OutpatientConditionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long Id;

    @Column(name = "conditions")
    private String Conditions;

    @Column(name = "created_at")
    private Timestamp CreatedAt;

    @OneToOne(mappedBy = "OutpatientCondition")
    private OutpatientEntity Outpatient;
}