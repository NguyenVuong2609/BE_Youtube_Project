package com.example.demo.service.comment;

import com.example.demo.model.Comment;

import java.util.Optional;

public interface ICommentService {
    void save(Comment comment);
    void deleteById(Long id);
    Optional<Comment> findById(Long id);
    Optional<Comment> findByIdAndOwnerId(Long id, Long userId);
}
