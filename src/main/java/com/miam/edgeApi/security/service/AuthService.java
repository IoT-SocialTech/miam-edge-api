package com.miam.edgeApi.security.service;

import com.miam.edgeApi.security.model.request.LoginRequestDto;
import com.miam.edgeApi.security.model.request.RegisterRequestDto;
import com.miam.edgeApi.security.model.response.RegisteredUserResponseDto;
import com.miam.edgeApi.security.model.response.TokenResponseDto;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;

public interface AuthService {

    ApiResponse<RegisteredUserResponseDto> registerUser(RegisterRequestDto request);

    ApiResponse<TokenResponseDto> login(LoginRequestDto request);

}
