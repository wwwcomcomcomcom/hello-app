package com.example.domain.post.dto;

import lombok.Getter;

@Getter
public class CreatePostDto {
    private String title;
    private String content;

    public CreatePostDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
