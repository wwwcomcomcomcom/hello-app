package com.example.global.security.jwt;

import com.example.domain.auth.dto.LoginResponseDto;
import com.example.global.security.jwt.properties.JwtEnvironment;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenGenerator {

    private final JwtEnvironment jwtEnv;

    private static final String TOKEN_TYPE = "tokenType";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";

    public LoginResponseDto generateToken(String userId) {
        return LoginResponseDto.builder()
                .accessToken(generateAccessToken(userId))
                .refreshToken(generateRefreshToken(userId))
                .accessTokenExp(jwtEnv.accessExp())
                .refreshTokenExp(jwtEnv.refreshExp())
                .build();
    }

    public String getUserIdFromRefreshToken(String token) {
        return getRefreshTokenSubject(token);
    }

    private String generateAccessToken(String userId) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtEnv.accessSecret().getBytes()), SignatureAlgorithm.HS256)
                .setSubject(userId)
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtEnv.accessExp() * 1000L))
                .compact();
    }

    private String generateRefreshToken(String userId) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(jwtEnv.refreshSecret().getBytes()), SignatureAlgorithm.HS256)
                .setSubject(userId)
                .claim(TOKEN_TYPE, REFRESH_TOKEN)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtEnv.refreshExp() * 1000L))
                .compact();
    }

    private String getRefreshTokenSubject(String refreshToken) {
        return getTokenBody(refreshToken, Keys.hmacShaKeyFor(jwtEnv.refreshSecret().getBytes())).getSubject();
    }

    public static Claims getTokenBody(String token, Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}