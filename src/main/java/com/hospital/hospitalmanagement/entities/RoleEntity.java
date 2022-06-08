package com.hospital.hospitalmanagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long Id;
    @Column(name = "name")
    private String Name;
    @Column(name = "created_at")
    private LocalDateTime CreatedAt;

    @OneToOne(mappedBy = "RoleId")
    private UserEntity User;
}
