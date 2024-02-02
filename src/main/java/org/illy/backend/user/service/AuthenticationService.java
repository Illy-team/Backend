package org.illy.backend.user.service;

import lombok.RequiredArgsConstructor;
import org.illy.backend.common.exception.CustomException;
import org.illy.backend.common.response.ErrorMessage;
import org.illy.backend.config.jwt.JwtService;
import org.illy.backend.user.dto.RegisterResponseDto;
import org.illy.backend.user.entity.Role;
import org.illy.backend.user.entity.User;
import org.illy.backend.user.repository.UserRepository;
import org.illy.backend.user.dto.LoginRequestDto;
import org.illy.backend.user.dto.RegisterRequestDto;
import org.illy.backend.user.dto.TokenDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public RegisterResponseDto register(RegisterRequestDto request) {
        repository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new CustomException(ErrorMessage.DUPLICATE_USER);});

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Role.USER)
                .build();
        user.encodePassword(passwordEncoder.encode(request.getPassword()));
        var res = repository.save(user);
        return RegisterResponseDto.builder()
                .userId(res.getId())
                .build();
    }

    public TokenDto login(LoginRequestDto request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorMessage.NOT_FOUND_USER_EXCEPTION));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorMessage.NOT_FOUND_USER_PASSWORD_EXCEPTION); // 비밀번호 검증

        }

        return TokenDto.builder()
                .userId(user.getId())
                .name(user.getName())
                .accessToken(jwtService.generateToken(user))
                .build(); // 토큰발급
    }
}
