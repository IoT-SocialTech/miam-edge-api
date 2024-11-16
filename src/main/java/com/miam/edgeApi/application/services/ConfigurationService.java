package com.miam.edgeApi.application.services;

import com.miam.edgeApi.application.dto.response.ConfigurationResponseDto;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;

import java.util.List;

public interface ConfigurationService {

    ApiResponse<ConfigurationResponseDto> getConfigurationById(int id);

    ApiResponse<List<ConfigurationResponseDto>> getConfigurationByDeviceId(String id);

}
