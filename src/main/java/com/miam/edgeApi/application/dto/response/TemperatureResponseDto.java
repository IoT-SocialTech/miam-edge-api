package com.miam.edgeApi.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemperatureResponseDto {

    private double temperature;

    private LocalDateTime date;

    private String status;

}
