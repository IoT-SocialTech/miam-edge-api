package com.miam.edgeApi.application.services.impl;

import com.miam.edgeApi.application.dto.response.ConfigurationResponseDto;
import com.miam.edgeApi.application.services.ConfigurationService;
import com.miam.edgeApi.domain.entities.Configuration;
import com.miam.edgeApi.infraestructure.repositories.ConfigurationRepository;
import com.miam.edgeApi.infraestructure.repositories.DeviceRepository;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import com.miam.edgeApi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {

    ConfigurationRepository configurationRepository;
    DeviceRepository deviceRepository;
    ModelMapper modelMapper;

    public ConfigurationServiceImpl(ConfigurationRepository configurationRepository, DeviceRepository deviceRepository, ModelMapper modelMapper) {
        this.configurationRepository = configurationRepository;
        this.deviceRepository = deviceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<ConfigurationResponseDto> getConfigurationById(int id) {
        Configuration configuration = configurationRepository.getConfigurationById(id);

        if (configuration == null) {
            return new ApiResponse<>("Configuration not found", Estatus.ERROR, null);
        } else {
            ConfigurationResponseDto response = modelMapper.map(configuration, ConfigurationResponseDto.class);
            return new ApiResponse<>("Configuration found successfully", Estatus.SUCCESS, response);
        }
    }

    @Override
    public ApiResponse<List<ConfigurationResponseDto>> getConfigurationByDeviceId(String id) {
        List<Configuration> configurations = configurationRepository.getConfigurationsByDeviceId(id);

        if (configurations.isEmpty()) {
            return new ApiResponse<>("Configurations not found", Estatus.ERROR, null);
        } else {
            List<ConfigurationResponseDto> response = configurations.stream()
                    .map(configuration -> modelMapper.map(configuration, ConfigurationResponseDto.class))
                    .toList();

            return new ApiResponse<>("Configurations found successfully", Estatus.SUCCESS, response);
        }
    }

}
