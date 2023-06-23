package com.example.demo.controller;

import com.example.demo.constant.Constant;
import com.example.demo.dto.request.ChannelDTO;
import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Channel;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.jwt.JwtTokenFilter;
import com.example.demo.security.userprincal.UserDetailService;
import com.example.demo.service.channel.ChannelServiceImpl;
import com.example.demo.service.user.UserServiceImpl;
import com.example.demo.service.video.VideoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("channel")
@CrossOrigin(origins = "*")
public class ChannelController {
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtTokenFilter jwtTokenFilter;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    VideoServiceImpl videoService;
    @Autowired
    private ChannelServiceImpl channelService;

    @GetMapping
    public ResponseEntity<?> showAllChannel() {
        return new ResponseEntity<>(channelService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createChannel(
            @Valid
            @RequestBody
            ChannelDTO channelDTO) {
        User user = userDetailService.getCurrentUser();
        Channel channel = new Channel(channelDTO.getChName(), channelDTO.getAvatar());
        if (channelService.existsByCh_name(channel.getChName())) {
            return new ResponseEntity<>(new ResponMessage(Constant.NAME_EXISTED), HttpStatus.OK);
        }
        if (channelService.existsByUserId(user.getId())) {
            return new ResponseEntity<>(new ResponMessage(Constant.CHANNEL_EXISTED), HttpStatus.OK);
        }
        channelService.save(channel);
        return new ResponseEntity<>(new ResponMessage(Constant.CREATE_SUCCESS), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> detailChannel(
            @PathVariable Long id) {
        Optional<Channel> channel = channelService.findByIdAndStatusIsTrue(id);
        if (!channel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        return new ResponseEntity<>(channel, HttpStatus.OK);
    }

    @GetMapping("{id}/videos")
    public ResponseEntity<?> getListVideoByChannelId(
            @PathVariable Long id) {
        Optional<Channel> channel = channelService.findByIdAndStatusIsTrue(id);
        if (!channel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        List<Video> videoList = (List<Video>) videoService.findAllByChannelId(id);
        return new ResponseEntity<>(videoList, HttpStatus.OK);
    }

    @GetMapping("/mychannel")
    public ResponseEntity<?> getMyChannel() {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Channel> channel = channelService.findByUserIdAndStatusIsTrue(user.getId());
        if (!channel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        return new ResponseEntity<>(channel, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateChannel(
            @Valid
            @PathVariable
            Long id,
            @RequestBody ChannelDTO channelDTO) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null){
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Channel> channel = channelService.findById(id);
        Optional<Channel> curChannel = channelService.findByUserIdAndStatusIsTrue(user.getId());
        if (!channel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        if (!channel.get().getChName().equals(channelDTO.getChName())) {
            if (channelService.existsByCh_name(channelDTO.getChName())) {
                return new ResponseEntity<>(new ResponMessage(Constant.NAME_EXISTED), HttpStatus.OK);
            }
        }
        if (channelDTO.getChName().equals(channel.get().getChName()) && channelDTO.getAvatar().equals(
                channel.get().getAvatar())) {
            return new ResponseEntity<>(new ResponMessage(Constant.NO_CHANGE), HttpStatus.OK);
        }
        channel.get().setChName(channelDTO.getChName());
        channel.get().setAvatar(channelDTO.getAvatar());
        if (!curChannel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        if (curChannel.get().getId() == channel.get().getId()) {
            channelService.save(channel.get());
            return new ResponseEntity<>(new ResponMessage(Constant.UPDATE_SUCCESSFUL), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponMessage(Constant.NO_PERMISSION), HttpStatus.OK);
        }
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> deleteChannel(
            @PathVariable Long id) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null){
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Channel> channel = channelService.findById(id);
        Optional<Channel> curChannel = channelService.findByUserIdAndStatusIsTrue(user.getId());
        if (!channel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        if (!curChannel.isPresent()) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_FOUND), HttpStatus.OK);
        }
        if (curChannel.get().getId() == channel.get().getId()) {
            channel.get().setStatus(false);
            channelService.save(channel.get());
            return new ResponseEntity<>(new ResponMessage(Constant.DELETE_SUCCESSFUL), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponMessage(Constant.NO_PERMISSION), HttpStatus.OK);
        }
    }
}
