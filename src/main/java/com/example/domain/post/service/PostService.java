package com.example.domain.post.service;

import com.example.domain.post.dto.CreatePostDto;
import com.example.domain.post.entity.Post;
import com.example.domain.post.repository.PostRepository;
import com.example.domain.user.entity.User;
import com.example.global.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserUtil userUtil;
    private final PostRepository postRepository;

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPost(Long id) {
        return postRepository.findById(id);
    }

    public Post createPost(CreatePostDto createPostDto) {
        User user = userUtil.getCurrentUser();
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "you are not logged in");
        }
        Post post = Post.builder()
                .title(createPostDto.getTitle())
                .content(createPostDto.getContent())
                .authorId(user.getId())
                .build();
        return postRepository.save(post);
    }

    public void deletePost(Long id) {
        User user = userUtil.getCurrentUser();
        if(user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "you are not logged in");

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post not found"));

        if(!post.getAuthorId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not the author of this post");

        postRepository.deleteById(id);
    }

    public void deleteUserPosts(Long userId) {
        postRepository.deleteAllByAuthorId(userId);
    }

}
