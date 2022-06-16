package com.hospital.hospitalmanagement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String phoneNumber;
    private String city;
    private String address;

    @JsonManagedReference
    @OneToMany(mappedBy = "patient",targetEntity = OutpatientEntity.class,cascade = CascadeType.ALL)
    private List<OutpatientEntity> outpatient;

    @OneToOne
    @JoinColumn(name = "blood_type_id", referencedColumnName = "id")
    private BloodTypeEntity bloodType;

    @OneToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private GenderEntity gender;
}
