package com.hospital.hospitalmanagement.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "dob")
    private LocalDate dob;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "available_from")
    private LocalTime availableFrom;
    @Column(name = "available_to")
    private LocalTime availableTo;

    @OneToOne()
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private DepartmentEntity department;

    @OneToOne()
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private RoleEntity role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "doctor",targetEntity = OutpatientEntity.class,cascade = CascadeType.ALL)
    private List<OutpatientEntity> outpatient;

}
