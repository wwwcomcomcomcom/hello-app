package com.example.global.util;

import com.example.domain.user.entity.User;
import com.example.domain.user.repository.UserRepository;
import com.example.global.security.auth.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof CustomUserDetails) {
            String userId = ((CustomUserDetails) principal).getUsername();
            return userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"유저를 찾을 수 없습니다."));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"현재 인증되어 있는 유저의 principal이 유효하지 않습니다.");
        }
    }

}
