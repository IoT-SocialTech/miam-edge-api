package com.miam.edgeApi.security.service;

import com.miam.edgeApi.domain.entities.Device;
import com.miam.edgeApi.infraestructure.repositories.DeviceRepository;
import com.miam.edgeApi.security.jwt.provider.JwtTokenProvider;
import com.miam.edgeApi.security.model.request.LoginRequestDto;
import com.miam.edgeApi.security.model.request.RegisterRequestDto;
import com.miam.edgeApi.security.model.response.RegisteredUserResponseDto;
import com.miam.edgeApi.security.model.response.TokenResponseDto;
import com.miam.edgeApi.shared.exception.ValidationException;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import com.miam.edgeApi.shared.model.enums.Estatus;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final DeviceRepository deviceRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    public AuthServiceImpl(AuthenticationManager authenticationManager, DeviceRepository deviceRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.deviceRepository = deviceRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApiResponse<RegisteredUserResponseDto> registerUser(RegisterRequestDto request) {
        //si el email ya está registrado
        validateAccount(request);

        //si no existe, lo registra
        var device = Device.builder()
                .id(request.getId())
                .model(request.getModel())
                .securityKey(passwordEncoder.encode(request.getSecurityKey()))
                .limitHeartRate(request.getLimitHeartRate())
                .limitTemperature(request.getLimitTemperature())
                .limitDistance(request.getLimitDistance())
                .status(request.getStatus())
                .patientId(request.getPatientId())
                .build();

        //guarda el usuario
        var newUser = deviceRepository.save(device);

        //mapea de la entidad al dto
        var responseData = modelMapper.map(newUser, RegisteredUserResponseDto.class);

        return new ApiResponse<>("Register Success", Estatus.SUCCESS, responseData);
    }

    @Override
    public ApiResponse<TokenResponseDto> login(LoginRequestDto request) {
        //se validan las credenciales
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getId(),
                        request.getPassword()
                )
        );

        //establece la seguridad
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //se obtiene el token
        String token = jwtTokenProvider.generateToken(authentication);

        //se obtiene usuario autenticado
        Optional<Device> device = deviceRepository.findDeviceById(request.getId());

        //var responseData = new TokenResponseDto(token);
        TokenResponseDto responseData = TokenResponseDto.builder()
                .id(device.get().getId())
                .token(token)
                .build();

        return new ApiResponse<>("Authentication Success", Estatus.SUCCESS, responseData);
    }


    private void validateAccount(RegisterRequestDto request) {
        if (isDeviceExist(request.getId())) {
            throw new ValidationException("The device provided is already registered");
        }
    }

    private boolean isDeviceExist(String id) {
        return deviceRepository.existsById(id);
    }



}
