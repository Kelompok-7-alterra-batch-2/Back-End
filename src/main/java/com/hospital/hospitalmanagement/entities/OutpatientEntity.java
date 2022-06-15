package com.hospital.hospitalmanagement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

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
    @Column(name = "name")
    private String name;

    @JsonBackReference
    @ManyToOne(targetEntity = PatientEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @JsonBackReference
    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity doctor;

    @OneToOne()
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private DepartmentEntity department;

    @OneToOne()
    @JoinColumn(name = "outpatient_condition_id", referencedColumnName = "id")
    private OutpatientConditionEntity outpatientCondition;

//    @OneToOne()
//    @JoinColumn(name = "queue", referencedColumnName = "id")
//    private QueueEntity queue;


    private int queue;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "arrival_time")
    private LocalTime arrivalTime;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
