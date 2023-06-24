package com.example.demo.controller;

import com.example.demo.constant.Constant;
import com.example.demo.dto.request.VideoDTO;
import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Channel;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.security.userprincal.UserDetailService;
import com.example.demo.service.channel.IChannelService;
import com.example.demo.service.video.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("video")
@CrossOrigin(origins = "*")
public class VideoController {
    @Autowired
    private IVideoService videoService;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private UserDetailService userDetailService;

    //! Show all videos //
    @GetMapping
    public ResponseEntity<?> showListVideo() {
        return new ResponseEntity<>(videoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<?> getRandomList(
            @PageableDefault(size = 6) Pageable pageable) {
        return new ResponseEntity<>(videoService.showRandomVideoList(pageable), HttpStatus.OK);
    }

//    @GetMapping("/page")
//    public ResponseEntity<?> showListVideoPage(
//            @PageableDefault(size = 6) Pageable pageable) {
//        return new ResponseEntity<>(videoService.findAll(pageable), HttpStatus.OK);
//    }

    @GetMapping("{id}")
    public ResponseEntity<?> detailVideo(
            @PathVariable Long id) {
        Optional<Video> video = videoService.findByIdAndStatusIsTrue(id);
        if (!video.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        videoService.updateView(id);
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @GetMapping("{id}/comment")
    public ResponseEntity<?> getListCommentByVideoId(
            @PathVariable Long id) {
        Optional<Video> video = videoService.findByIdAndStatusIsTrue(id);
        if (!video.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        return new ResponseEntity<>(videoService.findListCommentByVideoId(id), HttpStatus.OK);
    }
    @GetMapping("/topview")
    public ResponseEntity<?> getListVideoByTopViews(){
        return new ResponseEntity<>(videoService.findByStatusIsTrueOrderByViews(), HttpStatus.OK);
    }
    @GetMapping("/search/{name}")
    public ResponseEntity<?> getVideoListSearchByName(@PathVariable String name){
        return new ResponseEntity<>(videoService.findByNameContains(name), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createVideo(
            @Valid
            @RequestBody
            VideoDTO videoDTO) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Channel> channel = channelService.findByUserIdAndStatusIsTrue(user.getId());
        if (!channel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.CHANNEL_NOT_FOUND), HttpStatus.OK);
        }
        Video video = new Video(videoDTO.getName(), videoDTO.getLink(), videoDTO.getAvatar(),
                                videoDTO.getCategoryList());
        videoService.save(video);
        return new ResponseEntity<>(new ResponMessage(Constant.CREATE_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateVideo(
            @PathVariable Long id,
            @RequestBody VideoDTO videoDTO) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Channel> channel = channelService.findByUserIdAndStatusIsTrue(user.getId());
        if (!channel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.CHANNEL_NOT_FOUND), HttpStatus.OK);
        }
        Optional<Video> curVideo = videoService.findByIdAndChannelId(id, channel.get().getId());
        if (!curVideo.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NO_PERMISSION), HttpStatus.OK);
        }
        Optional<Video> video = videoService.findById(id);
        if (!video.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.VIDEO_NOT_FOUND), HttpStatus.OK);
        }
        curVideo.get().setName(videoDTO.getName());
        curVideo.get().setStatus(videoDTO.isStatus());
        curVideo.get().setVLink(videoDTO.getLink());
        curVideo.get().setCategoryList(videoDTO.getCategoryList());
        videoService.save(curVideo.get());
        return new ResponseEntity<>(new ResponMessage(Constant.UPDATE_SUCCESSFUL), HttpStatus.OK);
    }

    @PutMapping("{id}/like")
    private ResponseEntity<?> likeOrUnlikeVideo(
            @PathVariable Long id) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Video> video = videoService.findByIdAndStatusIsTrue(id);
        if (!video.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.VIDEO_NOT_FOUND), HttpStatus.OK);
        }
        List<User> likeList = video.get().getLikeList();
        Optional<User> checkLikeList = videoService.findUserLikeByVideoId(id, user.getId());
        if (!checkLikeList.isPresent()) {
            likeList.add(user);
            video.get().setLikeList(likeList);
            videoService.save(video.get());
            return new ResponseEntity<>(new ResponMessage("like_" + Constant.SUCCESSFUL), HttpStatus.OK);
        }
        video.get().setLikeList(likeList);
        likeList.remove(user);
        videoService.save(video.get());
        return new ResponseEntity<>(new ResponMessage("unlike_" + Constant.SUCCESSFUL), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteVideo(
            @PathVariable Long id) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Channel> channel = channelService.findByUserIdAndStatusIsTrue(user.getId());
        if (!channel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NO_PERMISSION), HttpStatus.OK);
        }
        Optional<Video> curVideo = videoService.findByIdAndChannelId(id, channel.get().getId());
        if (!curVideo.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NO_PERMISSION), HttpStatus.OK);
        }
        videoService.deleteById(curVideo.get().getId());
        return new ResponseEntity<>(new ResponMessage(Constant.DELETE_SUCCESSFUL), HttpStatus.OK);
    }
}
