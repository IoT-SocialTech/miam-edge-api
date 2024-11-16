package com.miam.edgeApi.application.controller;

import com.miam.edgeApi.application.dto.response.AverageHeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.AverageTemperatureResponseDto;
import com.miam.edgeApi.application.dto.response.HeartRateResponseDto;
import com.miam.edgeApi.application.dto.response.TemperatureResponseDto;
import com.miam.edgeApi.application.services.MetricsService;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Metrics", description = "Metrics API")
@RestController
@RequestMapping("/api/v1/miam-edge-api")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @Operation(summary = "Get average heart rate")
    @GetMapping("/metrics/averageHeartRate/{patientId}")
    public ResponseEntity<ApiResponse<AverageHeartRateResponseDto>> getAverageHeartRate(@PathVariable int patientId) {
        var res = metricsService.getAverageHeartRate(patientId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get average temperature")
    @GetMapping("/metrics/averageTemperature/{patientId}")
    public ResponseEntity<ApiResponse<AverageTemperatureResponseDto>> getAverageTemperature(@PathVariable int patientId) {
        var res = metricsService.getAverageTemperature(patientId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get temperature")
    @GetMapping("/metrics/temperature/{patientId}")
    public ResponseEntity<ApiResponse<TemperatureResponseDto>> getTemperature(@PathVariable int patientId) {
        var res = metricsService.getTemperature(patientId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Get heart rate")
    @GetMapping("/metrics/heartRate/{patientId}")
    public ResponseEntity<ApiResponse<HeartRateResponseDto>> getHeartRate(@PathVariable int patientId) {
        var res = metricsService.getHeartRate(patientId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
