package com.example.domain.auth.controller;

import com.example.domain.auth.dto.ReissueRequestDto;
import com.example.domain.auth.service.AuthenticationService;
import com.example.domain.auth.service.ReissueTokenService;
import com.example.domain.auth.dto.LoginRequestDto;
import com.example.domain.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final ReissueTokenService reissueTokenService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = authenticationService.login(loginRequestDto);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + response.getAccessToken())
                .body(response);
    }
    @PostMapping("/reissue")
    public ResponseEntity<LoginResponseDto> reissue(@RequestBody ReissueRequestDto reissueRequestDto) {
        LoginResponseDto response = reissueTokenService.execute(reissueRequestDto.getRefreshToken());
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + response.getAccessToken())
                .body(response);
    }
}
