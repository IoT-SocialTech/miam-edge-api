package com.miam.edgeApi.application.controller;

import com.miam.edgeApi.application.dto.request.DeviceRequestDto;
import com.miam.edgeApi.application.dto.request.UpdateLimitValues;
import com.miam.edgeApi.application.dto.response.DeviceResponseDto;
import com.miam.edgeApi.application.services.DeviceService;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Device", description = "Device API")
@RestController
@RequestMapping("/api/v1/miam-edge-api")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Operation(summary = "Create device")
    @PostMapping("/device")
    public ResponseEntity<ApiResponse<DeviceResponseDto>> createDevice(@RequestBody DeviceRequestDto deviceRequestDto) {
        var res = deviceService.createDevice(deviceRequestDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Get device by id")
    @GetMapping("/device/{id}")
    public ResponseEntity<ApiResponse<DeviceResponseDto>> getDeviceById(@PathVariable String id) {
        var res = deviceService.getDeviceById(id);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Update device")
    @PostMapping("/device/{id}")
    public ResponseEntity<ApiResponse<DeviceResponseDto>> updateDevice(@PathVariable String id, @RequestBody UpdateLimitValues updateLimitValues) {
        var res = deviceService.updateLimitsValues(id, updateLimitValues);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
