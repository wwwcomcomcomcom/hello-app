package com.example.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LoginResponseDto {
    private String accessToken;
    private String refreshToken;
    private int accessTokenExp;
    private int refreshTokenExp;
}
