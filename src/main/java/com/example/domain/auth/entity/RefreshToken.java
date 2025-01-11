package com.example.domain.auth.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refreshToken")
public class RefreshToken {
    @Id
    @Indexed
    private Long userId;
    @Indexed
    private String refreshToken;
    @TimeToLive
    private int expiresIn;
}
