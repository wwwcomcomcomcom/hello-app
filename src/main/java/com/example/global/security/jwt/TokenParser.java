package com.example.global.security.jwt;

import com.example.global.security.auth.CustomUserDetailsService;
import com.example.global.security.jwt.properties.JwtEnvironment;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.example.global.security.jwt.TokenGenerator.getTokenBody;

@Component
@RequiredArgsConstructor
public class TokenParser {

    private final JwtEnvironment jwtEnv;

    private final CustomUserDetailsService customUserDetailsService;

    public UsernamePasswordAuthenticationToken authenticate(String accessToken) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(getAccessTokenSubject(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getAccessTokenSubject(String accessToken) {
        return getTokenBody(accessToken, Keys.hmacShaKeyFor(jwtEnv.accessSecret().getBytes())).getSubject();
    }

}
