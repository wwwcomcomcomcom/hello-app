package com.example.domain.user.controller;

import com.example.domain.user.dto.CreateUserDto;
import com.example.domain.user.dto.LoginRequestDto;
import com.example.domain.user.dto.LoginResponseDto;
import com.example.domain.user.entity.User;
import com.example.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody CreateUserDto user) {
        userService.createUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto response = userService.login(loginRequestDto);
        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + response.getAccessToken())
                .body(response);
    }

    /** delete current logged in user*/
    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok("User deleted successfully");
    }
}
