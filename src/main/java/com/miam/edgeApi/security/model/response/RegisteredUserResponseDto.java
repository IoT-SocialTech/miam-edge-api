package com.miam.edgeApi.security.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUserResponseDto {

    private String id;

    private String model;

    private int limitHeartRate;

    private int limitTemperature;

    private int limitDistance;

    private String status;

    private int patientId;

}
