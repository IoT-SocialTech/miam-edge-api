package com.miam.edgeApi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "metrics")
public class Metrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "heart_rate")
    private int heartRate;

    @Column(name = "temperature")
    private int temperature;

    @Column(name = "alerts_generated")
    private int alertsGenerated;

    @Column(name = "distance_detector")
    private boolean distanceDetector;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "device_id")
    private int deviceId;

}
