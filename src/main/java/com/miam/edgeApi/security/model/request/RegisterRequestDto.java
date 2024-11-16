package com.miam.edgeApi.security.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequestDto {

    @NotBlank(message = "Id is mandatory")
    private String id;

    @NotBlank(message = "Model is mandatory")
    private String model;

    @NotBlank(message = "Limit heart rate is mandatory")
    private int limitHeartRate;

    @NotBlank(message = "Limit temperature is mandatory")
    private int limitTemperature;

    @NotBlank(message = "Limit distance is mandatory")
    private int limitDistance;

    @NotBlank(message = "Security key is mandatory")
    private String securityKey;

    @NotBlank(message = "Status is mandatory")
    private String status;

    @NotBlank(message = "Patient id is mandatory")
    private int patientId;

}
