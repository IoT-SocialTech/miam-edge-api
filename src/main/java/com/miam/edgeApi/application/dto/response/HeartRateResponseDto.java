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
public class HeartRateResponseDto {

    private int heartRate;

    private LocalDateTime date;

    private String status;

}
