package com.miam.edgeApi.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "configurations")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "config_name", nullable = false)
    private String configName;

    @Column(name = "config_values", nullable = false)
    private String configValues;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

}
