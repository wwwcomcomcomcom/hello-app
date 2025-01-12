package com.example.domain.post.dto;

import com.example.domain.post.entity.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostListDto {
    private int length;
    private List<Post> posts;
    public PostListDto(List<Post> posts) {
        this.posts = posts;
        this.length = posts.size();
    }
}
