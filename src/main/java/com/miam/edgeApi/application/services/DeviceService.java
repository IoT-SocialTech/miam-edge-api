package com.miam.edgeApi.application.services;

import com.miam.edgeApi.application.dto.request.DeviceRequestDto;
import com.miam.edgeApi.application.dto.request.UpdateLimitValues;
import com.miam.edgeApi.application.dto.response.DeviceResponseDto;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;

public interface DeviceService {

    ApiResponse<DeviceResponseDto> createDevice(DeviceRequestDto deviceRequestDto);

    ApiResponse<DeviceResponseDto> updateLimitsValues(String id, UpdateLimitValues updateLimitValues);

    ApiResponse<DeviceResponseDto> getDeviceById(String id);

}
