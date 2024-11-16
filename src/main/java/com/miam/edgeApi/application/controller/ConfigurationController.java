package com.miam.edgeApi.application.controller;

import com.miam.edgeApi.application.dto.response.ConfigurationResponseDto;
import com.miam.edgeApi.application.services.ConfigurationService;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Configuration", description = "Configuration API")
@RestController
@RequestMapping("/api/v1/miam-edge-api")
public class ConfigurationController {

    ConfigurationService configurationService;

    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @Operation(summary = "get a configuration by id")
    @GetMapping("/configurations/{id}")
    public ResponseEntity<ApiResponse<ConfigurationResponseDto>> getConfigurationById(@PathVariable int id) {
        var res = configurationService.getConfigurationById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "get configurations by device id")
    @GetMapping("/configurations/device/{id}")
    public ResponseEntity<ApiResponse<List<ConfigurationResponseDto>>> getConfigurationByDeviceId(@PathVariable String id) {
        var res = configurationService.getConfigurationByDeviceId(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
