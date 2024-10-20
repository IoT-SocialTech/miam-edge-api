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
public class AverageHeartRateResponseDto {

    private double averageHeartRate;

    private LocalDateTime date;

    private String status;


}
