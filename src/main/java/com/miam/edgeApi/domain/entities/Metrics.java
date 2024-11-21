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
    private double heartRate;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "panic_button")
    private Boolean panicButton;

    @Column(name = "distance")
    private double distance;

    @Column(name = "distance_detector")
    private Boolean distanceDetector;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "patient_id")
    private int patientId;

    @Column(name = "device_id")
    private String deviceId;

}
