package com.example.demo.controller;

import com.example.demo.constant.Constant;
import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.History;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.security.userprincal.UserDetailService;
import com.example.demo.service.history.IHistoryService;
import com.example.demo.service.user.IUserService;
import com.example.demo.service.video.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("history")
@CrossOrigin(origins = "*")
public class HistoryController {
    @Autowired
    private IHistoryService historyService;
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IVideoService videoService;

    @GetMapping("{id}")
    public ResponseEntity<?> getListVideoByHistoryId(
            @PathVariable("id") Long id) {
        Optional<History> history = historyService.findById(id);
        if (!history.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        return new ResponseEntity<>(historyService.getVideoListByHistoryId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createHistory(
            @RequestBody String username) {
        Optional<History> history = historyService.findByOwnerUsername(username);
        Optional<User> user = userService.findByUsername(username);
        if (history.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.HISTORY_EXISTED), HttpStatus.OK);
        }
        if (!user.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        History newHistory = new History();
        newHistory.setOwner(user.get());
        historyService.save(newHistory);
        return new ResponseEntity<>(new ResponMessage(Constant.CREATE_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("add/{id}")
    public ResponseEntity<?> addVideo(
            @PathVariable Long id) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NO_PERMISSION), HttpStatus.OK);
        }
        Optional<Video> newVideo = videoService.findByIdAndStatusIsTrue(id);
        if (!newVideo.isPresent()){
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        Optional<History> history = historyService.findByOwnerId(user.getId());
        if (!history.isPresent()){
            return new ResponseEntity<>(new ResponMessage("history_" +Constant.NOT_FOUND), HttpStatus.OK);
        }
        List<Video> videoList = history.get().getVideoList();
        Optional<Video> video = historyService.findVideoByHistory(id, history.get().getId());
        if (video.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.HISTORY_EXISTED), HttpStatus.OK);
        }
        videoList.add(newVideo.get());
        history.get().setVideoList(videoList);
        historyService.save(history.get());
        return new ResponseEntity<>(new ResponMessage(Constant.ADD_SUCCESSFUL), HttpStatus.OK);
    }
    @PutMapping("/remove/{id}")
    public ResponseEntity<?> removeVideo(@PathVariable Long id){
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NO_PERMISSION), HttpStatus.OK);
        }
        Optional<Video> video = videoService.findByIdAndStatusIsTrue(id);
        if (!video.isPresent()){
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        Optional<History> history = historyService.findByOwnerId(user.getId());
        if (!history.isPresent()){
            return new ResponseEntity<>(new ResponMessage("history_" +Constant.NOT_FOUND), HttpStatus.OK);
        }
        List<Video> videoList = history.get().getVideoList();
        Optional<Video> oldVideo = historyService.findVideoByHistory(id, history.get().getId());
        if (!oldVideo.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.UNSUCCESSFUL), HttpStatus.OK);
        }
        videoList.remove(oldVideo.get());
        history.get().setVideoList(videoList);
        historyService.save(history.get());
        return new ResponseEntity<>(new ResponMessage(Constant.REMOVE_SUCCESSFUL), HttpStatus.OK);
    }
}
