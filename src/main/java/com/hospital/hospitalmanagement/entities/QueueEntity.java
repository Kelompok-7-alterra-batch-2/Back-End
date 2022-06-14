package com.hospital.hospitalmanagement.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "queue")
public class QueueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Long id;
    @Column(name = "queue_number")
    private Integer queueNumber;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
