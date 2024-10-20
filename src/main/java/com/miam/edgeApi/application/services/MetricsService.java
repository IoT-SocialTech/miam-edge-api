package com.miam.edgeApi.application.services;

import com.miam.edgeApi.application.dto.response.AverageHeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.AverageTemperatureResponseDto;
import com.miam.edgeApi.application.dto.response.HeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.TemperatureResponseDto;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;

public interface MetricsService {

    ApiResponse<AverageHeartRateResponseDto> getAverageHeartRate();

    ApiResponse<AverageTemperatureResponseDto> getAverageTemperature();

    ApiResponse<HeartRateResponseDto> getHeartRate();

    ApiResponse<TemperatureResponseDto> getTemperature();

}
