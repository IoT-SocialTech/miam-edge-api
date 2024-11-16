package com.miam.edgeApi.security.controller;

import com.miam.edgeApi.security.model.request.LoginRequestDto;
import com.miam.edgeApi.security.model.request.RegisterRequestDto;
import com.miam.edgeApi.security.model.response.RegisteredUserResponseDto;
import com.miam.edgeApi.security.model.response.TokenResponseDto;
import com.miam.edgeApi.security.service.AuthService;
import com.miam.edgeApi.shared.model.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth")
@SecurityRequirements //desactiva la seguridad para este controlador (swagger)
@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Operation(summary = "Log in to the system")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<TokenResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
        var res = service.login(request);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisteredUserResponseDto>> registerUser(@Valid @RequestBody RegisterRequestDto request) {
        var res = service.registerUser(request);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
