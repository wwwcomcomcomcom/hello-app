package com.example.domain.post.repository;

import com.example.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    void deleteAllByAuthorId(Long authorId);
}
