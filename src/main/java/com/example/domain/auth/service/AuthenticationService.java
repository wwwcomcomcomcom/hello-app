package com.example.domain.auth.service;

import com.example.domain.auth.dto.LoginRequestDto;
import com.example.domain.auth.dto.LoginResponseDto;
import com.example.domain.user.entity.User;
import com.example.domain.user.repository.UserRepository;
import com.example.global.security.jwt.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenGenerator tokenGenerator;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findUserByName(loginRequestDto.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        if(!passwordEncoder.matches(loginRequestDto.getPassword(),user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is incorrect");
        }
        LoginResponseDto dto = tokenGenerator.generateToken(String.valueOf(user.getId()));

        return dto;
    }
}
