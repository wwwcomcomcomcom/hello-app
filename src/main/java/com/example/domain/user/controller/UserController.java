package com.example.domain.user.controller;

import com.example.domain.user.dto.CreateUserDto;
import com.example.domain.user.dto.UserDto;
import com.example.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody CreateUserDto user) {
        userService.createUser(user);
        return ResponseEntity.ok("User created successfully");
    }

    /** delete current logged in user*/
    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        userService.deleteUser();
        return ResponseEntity.ok("User deleted successfully");
    }
}
