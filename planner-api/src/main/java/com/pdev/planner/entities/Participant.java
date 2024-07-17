package com.pdev.planner.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "participants")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "is_confirmed", nullable = false)
    private boolean isConfirmed;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

}
