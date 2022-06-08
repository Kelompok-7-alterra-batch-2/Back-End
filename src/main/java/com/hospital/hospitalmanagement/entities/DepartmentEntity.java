package com.hospital.hospitalmanagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long Id;
    @Column(name = "name")
    private String Name;
    @Column(name = "created_at")
    private Timestamp CreatedAt;

    @OneToOne(mappedBy = "DepartmentId")
    private UserEntity User;

    @OneToOne(mappedBy = "DepartmentId")
    private OutpatientEntity Outpatient;
}