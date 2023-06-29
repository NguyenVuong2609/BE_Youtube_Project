package com.example.demo.controller;

import com.example.demo.constant.Constant;
import com.example.demo.dto.request.CommentDTO;
import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.security.userprincal.UserDetailService;
import com.example.demo.service.comment.ICommentService;
import com.example.demo.service.video.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("comment")
@CrossOrigin(origins = "*")
public class    CommentController {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IVideoService videoService;
    @Autowired
    private ICommentService commentService;

    @PostMapping("{id}")
    public ResponseEntity<?> createComment(
            @PathVariable Long id,
            @RequestBody CommentDTO commentDTO) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Video> video = videoService.findByIdAndStatusIsTrue(id);
        if (!video.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setOwner(user);
        comment.setVideo(video.get());
        commentService.save(comment);
        return new ResponseEntity<>(new ResponMessage(Constant.CREATE_SUCCESS), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> showListComment(
            @PathVariable Long id) {
        Optional<Video> video = videoService.findByIdAndStatusIsTrue(id);
        if (!video.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        Iterable<Comment> commentList = videoService.findListCommentByVideoId(id);
        return new ResponseEntity<>(commentList, HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null){
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Comment> comment = commentService.findById(id);
        if (!comment.isPresent()){
            return new ResponseEntity<>(new ResponMessage(Constant.COMMENT_NOT_FOUND), HttpStatus.OK);
        }
        Optional<Comment> checkComment = commentService.findByIdAndOwnerId(id, user.getId());
        if (checkComment.isPresent()){
            commentService.deleteById(id);
            return new ResponseEntity<>(new ResponMessage(Constant.REMOVE_SUCCESSFUL), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponMessage(Constant.NO_PERMISSION), HttpStatus.OK);
    }
}
