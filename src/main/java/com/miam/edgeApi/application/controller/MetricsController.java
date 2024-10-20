package com.miam.edgeApi.application.controller;

import com.miam.edgeApi.application.dto.response.TemperatureResponseDto;
import com.miam.edgeApi.application.services.MetricsService;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Operation(summary = "Get temperature")
    @GetMapping("/metrics/temperature")
    public ResponseEntity<ApiResponse<TemperatureResponseDto>> getTemperature() {
        var res = metricsService.getTemperature();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
