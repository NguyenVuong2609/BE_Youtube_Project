package com.example.demo.service.video;

import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.service.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IVideoService extends IGenericService<Video> {
    Page<Video> findAll(Pageable pageable);
    Optional<Video> findByIdAndChannelId(Long vId, Long chId);
    void updateView(Long id);
    Iterable<Video> findAllByChannelId(Long id);
    Optional<Video> findByIdAndStatusIsTrue(Long id);
    Optional<User> findUserLikeByVideoId(Long vId, Long uId);
    Page<Video> findAllByStatusIsTrue(Pageable pageable);
    Iterable<Comment> findListCommentByVideoId(Long id);
    Iterable<Video> showRandomVideoList(Pageable pageable);
    Iterable<Video> findByStatusIsTrueOrderByViews();
    Iterable<Video> findByNameContains(String name);
}
