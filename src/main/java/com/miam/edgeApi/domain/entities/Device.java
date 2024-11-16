package com.miam.edgeApi.domain.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "devices")
public class Device {

    @Id
    private String id;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "limit_heart_rate", nullable = false)
    private int limitHeartRate;

    @Column(name = "limit_temperature", nullable = false)
    private int limitTemperature;

    @Column(name = "limit_distance", nullable = false)
    private int limitDistance;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "patient_id", nullable = false)
    private int patientId;

}
