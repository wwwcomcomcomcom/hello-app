package com.example.domain.post.controller;

import com.example.domain.post.dto.CreatePostDto;
import com.example.domain.post.dto.PostListDto;
import com.example.domain.post.entity.Post;
import com.example.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public PostListDto getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        if(posts.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No posts found");
        return new PostListDto(posts);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable Long id) {
        return postService.getPost(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));
    }

    @PostMapping
    public Post createPost(@RequestBody CreatePostDto createPostDto) {
        return postService.createPost(createPostDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok("Post deleted successfully");
    }
}
