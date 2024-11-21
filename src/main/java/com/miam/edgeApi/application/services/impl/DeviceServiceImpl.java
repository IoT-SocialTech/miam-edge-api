package com.miam.edgeApi.application.services.impl;

import com.miam.edgeApi.application.dto.request.DeviceRequestDto;
import com.miam.edgeApi.application.dto.request.UpdateLimitValues;
import com.miam.edgeApi.application.dto.response.DeviceResponseDto;
import com.miam.edgeApi.application.services.DeviceService;
import com.miam.edgeApi.domain.entities.Configuration;
import com.miam.edgeApi.domain.entities.Device;
import com.miam.edgeApi.infraestructure.repositories.ConfigurationRepository;
import com.miam.edgeApi.infraestructure.repositories.DeviceRepository;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import com.miam.edgeApi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<DeviceResponseDto> createDevice(DeviceRequestDto deviceRequestDto) {
        Device device = modelMapper.map(deviceRequestDto, Device.class);
        deviceRepository.save(device);
        DeviceResponseDto deviceResponseDto = modelMapper.map(device, DeviceResponseDto.class);

        return new ApiResponse<>("Device created successfully", Estatus.SUCCESS, deviceResponseDto);
    }

    @Override
    public ApiResponse<DeviceResponseDto> getDeviceById(String id) {
        Device device = deviceRepository.getDeviceById(id);
        if (device == null) {
            return new ApiResponse<>("Device not found", Estatus.ERROR, null);
        } else {
            DeviceResponseDto deviceResponseDto = modelMapper.map(device, DeviceResponseDto.class);
            return new ApiResponse<>("Device found", Estatus.SUCCESS, deviceResponseDto);
        }
    }

    @Override
    public ApiResponse<DeviceResponseDto> updateLimitsValues(String id, UpdateLimitValues updateLimitValues) {
        Device device = deviceRepository.getDeviceById(id);
        if (device == null) {
            return new ApiResponse<>("Device not found", Estatus.ERROR, null);
        } else {
            device.setLimitDistance(updateLimitValues.getLimitDistance());
            device.setLimitTemperature(updateLimitValues.getLimitTemperature());
            device.setLimitHeartRate(updateLimitValues.getLimitHeartRate());
            deviceRepository.save(device);

            Configuration configuration = new Configuration();
            configuration.setDeviceId(id);
            configuration.setConfigName("Update Limits");
            configuration.setConfigValues("Distance: " + updateLimitValues.getLimitDistance() + " Temperature: " + updateLimitValues.getLimitTemperature() + " Heart Rate: " + updateLimitValues.getLimitHeartRate());
            configurationRepository.save(configuration);

            DeviceResponseDto deviceResponseDto = modelMapper.map(device, DeviceResponseDto.class);
            return new ApiResponse<>("Device updated successfully", Estatus.SUCCESS, deviceResponseDto);
        }
    }

}
