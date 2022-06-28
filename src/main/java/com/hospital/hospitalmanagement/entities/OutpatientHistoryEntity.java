package com.hospital.hospitalmanagement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "outpatient_history")
public class OutpatientHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(unique = true)
//    private String medicalRecord;

    private int queue;

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
    private String appointmentReason;

    private Long id_today;

    @Column(name = "date")
    private LocalDate date;
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
