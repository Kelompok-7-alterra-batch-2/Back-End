package com.hospital.hospitalmanagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long Id;
    @Column(name = "name")
    private String Name;
    @Column(name = "dob")
    private Date Dob;
    @Column(name = "password")
    private String Password;
    @Column(name = "email")
    private String Email;
    @Column(name = "available_from")
    private LocalDateTime AvailableFrom;
    @Column(name = "available_to")
    private LocalDateTime AvailableTo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private DepartmentEntity DepartmentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity RoleId;

    @Column(name = "created_at")
    private LocalDateTime CreatedAt;

    @OneToMany(mappedBy = "DoctorId",targetEntity = OutpatientEntity.class,cascade = CascadeType.ALL)
    private List<OutpatientEntity> Outpatient;

}
