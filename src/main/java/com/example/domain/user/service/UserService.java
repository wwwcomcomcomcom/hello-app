package com.example.domain.user.service;

import com.example.domain.post.repository.PostRepository;
import com.example.domain.user.dto.CreateUserDto;
import com.example.domain.user.entity.User;
import com.example.domain.user.repository.UserRepository;
import com.example.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtil userUtil;

    public void createUser(CreateUserDto createUserDto) {
        if(isUserExist(createUserDto.getUsername())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        User user = User.builder()
                .name(createUserDto.getUsername())
                .password(
                        passwordEncoder.encode(createUserDto.getPassword())
                )
                .build();
        userRepository.save(user);
    }

    public void deleteUser() {
        User user = userUtil.getCurrentUser();
        if(user == null) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "you are not logged in");
        postRepository.deleteAllByAuthorId(user.getId());
        userRepository.deleteById(user.getId());
    }

    private boolean isUserExist(String username) {
        return userRepository.findUserByName(username).isPresent();
    }
}
