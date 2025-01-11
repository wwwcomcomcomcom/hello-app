package com.example.domain.user.service;

import com.example.domain.user.dto.CreateUserDto;
import com.example.domain.user.entity.User;
import com.example.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(CreateUserDto createUserDto) {
        if(isUserNotExist(createUserDto.getUsername())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        User user = User.builder()
                .name(createUserDto.getUsername())
                .password(createUserDto.getPassword())
                .build();
        userRepository.save(user);
    }

    private boolean isUserNotExist(String username) {
        return userRepository.findUserByName(username).isEmpty();
    }
}
