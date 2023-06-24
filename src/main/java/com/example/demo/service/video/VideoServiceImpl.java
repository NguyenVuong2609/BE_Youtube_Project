package com.example.demo.service.video;

import com.example.demo.model.Channel;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.repository.IChannelRepository;
import com.example.demo.repository.IVideoRepository;
import com.example.demo.security.userprincal.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoServiceImpl implements IVideoService {
    @Autowired
    private IVideoRepository videoRepository;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IChannelRepository channelRepository;

    @Override
    public Iterable<Video> findAll() {
        return videoRepository.findAllByStatusIsTrue();
    }

    @Override
    public Page<Video> findAll(Pageable pageable) {
        return videoRepository.findAll(pageable);
    }

    @Override
    public Optional<Video> findByIdAndChannelId(Long vId, Long chId) {
        return videoRepository.findByIdAndChannelId(vId,chId);
    }

    @Override
    public void updateView(Long id) {
        Optional<Video> video = findById(id);
        video.get().setViews(video.get().getViews() + 1);
        videoRepository.save(video.get());
    }

    @Override
    public Iterable<Video> findAllByChannelId(Long id) {
        return videoRepository.findAllByChannelIdAndStatusIsTrue(id);
    }

    @Override
    public Optional<Video> findByIdAndStatusIsTrue(Long id) {
        return videoRepository.findByIdAndStatusIsTrue(id);
    }

    @Override
    public Optional<User> findUserLikeByVideoId(Long vId, Long uId) {
        return videoRepository.findUserLikeByVideoId(vId,uId);
    }

    @Override
    public Page<Video> findAllByStatusIsTrue(Pageable pageable) {
        return videoRepository.findAllByStatusIsTrue(pageable);
    }

    @Override
    public Iterable<Comment> findListCommentByVideoId(Long id) {
        return videoRepository.findListCommentByVideoId(id);
    }

    @Override
    public Iterable<Video> showRandomVideoList(Pageable pageable) {
        return videoRepository.showRandomVideoList(pageable);
    }

    @Override
    public Iterable<Video> findByStatusIsTrueOrderByViews() {
        return videoRepository.findByStatusIsTrueOrderByViewsDesc();
    }

    @Override
    public Iterable<Video> findByNameContains(String name) {
        return videoRepository.findByNameContainsAndStatusIsTrue(name);
    }

    @Override
    public Optional<Video> findById(Long id) {
        return videoRepository.findById(id);
    }

    @Override
    public void save(Video video) {
        User user = userDetailService.getCurrentUser();
        Channel channel = channelRepository.findByUserIdAndStatusIsTrue(user.getId()).get();
        video.setChannel(channel);
        videoRepository.save(video);
    }

    @Override
    public void deleteById(Long id) {
        videoRepository.deleteById(id);
    }


}
