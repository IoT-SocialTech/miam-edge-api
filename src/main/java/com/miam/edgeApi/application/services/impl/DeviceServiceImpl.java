package com.miam.edgeApi.application.services.impl;

import com.miam.edgeApi.application.dto.request.DeviceRequestDto;
import com.miam.edgeApi.application.dto.response.DeviceResponseDto;
import com.miam.edgeApi.application.services.DeviceService;
import com.miam.edgeApi.domain.entities.Device;
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
    private ModelMapper modelMapper;

    @Override
    public ApiResponse<DeviceResponseDto> createDevice(DeviceRequestDto deviceRequestDto) {
        Device device = modelMapper.map(deviceRequestDto, Device.class);
        deviceRepository.save(device);
        DeviceResponseDto deviceResponseDto = modelMapper.map(device, DeviceResponseDto.class);

        return new ApiResponse<>("Device created successfully", Estatus.SUCCESS, deviceResponseDto);
    }

}
