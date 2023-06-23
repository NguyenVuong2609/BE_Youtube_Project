package com.example.demo.service.comment;

import com.example.demo.model.Comment;
import com.example.demo.repository.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements ICommentService{
    @Autowired
    private ICommentRepository commentRepository;
    @Override
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }
}
