package com.hospital.hospitalmanagement.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "queue")
public class QueueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long Id;
    @Column(name = "queue_number")
    private Integer QueueNumber;
    @Column(name = "created_at")
    private Timestamp CreatedAt;

    @OneToOne(mappedBy = "Queue")
    private OutpatientEntity Outpatient;
}
