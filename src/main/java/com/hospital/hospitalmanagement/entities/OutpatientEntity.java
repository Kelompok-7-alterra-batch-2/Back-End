package com.hospital.hospitalmanagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "outpatient")
public class OutpatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long Id;
    @Column(name = "name")
    private String Name;

    @ManyToOne(targetEntity = PatientEntity.class)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity PatientId;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity DoctorId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private DepartmentEntity DepartmentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "outpatient_condition_id", referencedColumnName = "id")
    private OutpatientConditionEntity OutpatientCondition;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "queue", referencedColumnName = "id")
    private QueueEntity Queue;

    @Column(name = "date")
    private Date Date;
    @Column(name = "arrival_time")
    private LocalDateTime ArrivalTime;
    @Column(name = "created_at")
    private LocalDateTime CreatedAt;
}
