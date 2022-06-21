package com.hospital.hospitalmanagement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "outpatient")
public class OutpatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonBackReference
    @ManyToOne(targetEntity = PatientEntity.class)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @JsonBackReference
    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity doctor;

    @OneToOne()
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private DepartmentEntity department;

    @OneToOne()
    @JoinColumn(name = "outpatient_condition_id", referencedColumnName = "id")
    private OutpatientConditionEntity outpatientCondition;

    private String diagnosis;
    private String prescription;
    private String medicalRecord;
    private String appointmentReason;
    private Long dokter;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int queue;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
