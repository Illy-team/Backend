package org.illy.backend.user.controller;

import lombok.RequiredArgsConstructor;
import org.illy.backend.common.response.ApiResponse;
import org.illy.backend.common.response.SuccessMessage;
import org.illy.backend.user.dto.LoginRequestDto;
import org.illy.backend.user.dto.RegisterRequestDto;
import org.illy.backend.user.dto.RegisterResponseDto;
import org.illy.backend.user.dto.TokenDto;
import org.illy.backend.user.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ApiResponse<RegisterResponseDto> register(@RequestBody RegisterRequestDto request) {
        RegisterResponseDto data = service.register(request);
        return ApiResponse.success(SuccessMessage.SIGNUP_SUCCESS, data);
    }

    @PostMapping("/login")
    public ApiResponse<TokenDto> login(@RequestBody LoginRequestDto request) {
        TokenDto data = service.login(request);
        return ApiResponse.success(SuccessMessage.LOGIN_SUCCESS, data);
    }
}
