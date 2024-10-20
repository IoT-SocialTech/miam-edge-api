package com.miam.edgeApi.application.services;

import com.miam.edgeApi.application.dto.response.HeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.TemperatureResponseDto;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;

public interface MetricsService {

    ApiResponse<HeartRateResponseDto> getHeartRate();

    ApiResponse<TemperatureResponseDto> getTemperature();

}
