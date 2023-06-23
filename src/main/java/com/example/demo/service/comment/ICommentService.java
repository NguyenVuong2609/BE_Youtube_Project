package com.example.demo.service.comment;

import com.example.demo.model.Comment;

public interface ICommentService {
    void save(Comment comment);
    void deleteById(Long id);
}
