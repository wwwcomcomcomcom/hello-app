package com.example.domain.user.dto;

import com.example.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {
    private long id;
    private String name;
    static public UserDto of(User user) {
        return new UserDto(user.getId(), user.getName());
    }
}
