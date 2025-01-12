package com.example.domain.auth.service;

import com.example.domain.auth.entity.RefreshToken;
import com.example.domain.auth.repository.RefreshTokenRepository;
import com.example.domain.auth.dto.LoginResponseDto;
import com.example.domain.user.repository.UserRepository;
import com.example.global.security.jwt.TokenGenerator;
import com.example.global.security.jwt.properties.JwtEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static com.example.global.security.filter.JwtReqFilter.BEARER_PREFIX;

@Service
@RequiredArgsConstructor
public class ReissueTokenService {

    private final JwtEnvironment jwtEnv;
    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public LoginResponseDto execute(String token) {
        isNotNullRefreshToken(token);

        String removePrefixToken = token.replaceFirst(BEARER_PREFIX, "").trim();
        RefreshToken refreshToken = refreshTokenRepository.findByRefreshToken(removePrefixToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"존재하지 않는 refresh token 입니다."));

        String userId = tokenGenerator.getUserIdFromRefreshToken(refreshToken.getRefreshToken());
        isExistsUser(userId);

        LoginResponseDto tokenDto = tokenGenerator.generateToken(userId);
        saveNewRefreshToken(tokenDto.getRefreshToken(), refreshToken.getUserId());
        return tokenDto;
    }

    private void isNotNullRefreshToken(String token) {
        if (token == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "refresh token을 요청 헤더에 포함시켜 주세요.");
    }

    private void isExistsUser(String userId) {
        if (!userRepository.existsById(Long.valueOf(userId)))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"유저를 찾을 수 없습니다.");
    }

    private void saveNewRefreshToken(String token, Long userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .refreshToken(token)
                .expiresIn(jwtEnv.refreshExp())
                .build();

        refreshTokenRepository.save(refreshToken);
    }

}
