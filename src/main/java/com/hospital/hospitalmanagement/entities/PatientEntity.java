package com.hospital.hospitalmanagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long Id;
    @Column(name = "name")
    private String Name;
    @Column(name = "medical_record")
    private Long MedicalRecord;
    @Column(name = "dob")
    private Date Dob;
    @Column(name = "created_at")
    private Timestamp CreatedAt;

    @OneToMany(mappedBy = "PatientId",targetEntity = OutpatientEntity.class,cascade = CascadeType.ALL)
    private List<OutpatientEntity> OutpatientId;
}
