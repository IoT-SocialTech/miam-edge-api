package com.miam.edgeApi.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceResponseDto {

    private String id;

    private String model;

    private int limitHeartRate;

    private int limitTemperature;

    private int limitDistance;

    private String status;

    private int patientId;

}
