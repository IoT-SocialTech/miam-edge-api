package com.miam.edgeApi.application.services;

import com.miam.edgeApi.application.dto.request.CreateMetricsDto;
import com.miam.edgeApi.application.dto.response.*;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import org.json.JSONObject;

public interface MetricsService {

    ApiResponse<CreateMetricsResponseDto> createMetrics(CreateMetricsDto createMetricsDto);

    ApiResponse<AverageHeartRateResponseDto> getAverageHeartRate(int id);

    ApiResponse<AverageTemperatureResponseDto> getAverageTemperature(int id);

    ApiResponse<HeartRateResponseDto> getHeartRate(int id);

    ApiResponse<TemperatureResponseDto> getTemperature(int id);

}
