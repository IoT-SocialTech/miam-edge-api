package com.miam.edgeApi.application.services;

import com.miam.edgeApi.application.dto.request.DeviceRequestDto;
import com.miam.edgeApi.application.dto.response.DeviceResponseDto;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;

public interface DeviceService {

    ApiResponse<DeviceResponseDto> createDevice(DeviceRequestDto deviceRequestDto);

}
