package com.example.demo.controller;

import com.example.demo.constant.Constant;
import com.example.demo.dto.request.PlaylistDTO;
import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Playlist;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.security.userprincal.UserDetailService;
import com.example.demo.service.playlist.IPlaylistService;
import com.example.demo.service.video.IVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("playlist")
@CrossOrigin(origins = "*")
public class PlaylistController {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IPlaylistService playlistService;
    @Autowired
    private IVideoService videoService;

    @GetMapping
    public ResponseEntity<?> showAllPlaylist() {
        return new ResponseEntity<>(playlistService.findAll(), HttpStatus.OK);
    }
    @GetMapping("{id}")
    public ResponseEntity<?> showDetailPlaylist(@PathVariable Long id){
        Optional<Playlist> playlist = playlistService.findByIdAndStatusIsTrue(id);
        if (!playlist.isPresent()){
            return new ResponseEntity<>(new ResponMessage(Constant.PLAYLIST_NOT_FOUND), HttpStatus.OK);
        }
        return new ResponseEntity<>(playlist, HttpStatus.OK);
    }
    @GetMapping("/myplaylist")
    public ResponseEntity<?> showAllMyPlaylist(){
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null){
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        return new ResponseEntity<>(playlistService.findByUserId(user.getId()), HttpStatus.OK);
    }
    @GetMapping("/myplaylist/{id}")
    public ResponseEntity<?> showVideoListByPlaylist(@PathVariable Long id){
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null){
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Playlist> playlist = playlistService.findByIdAndStatusIsTrue(id);
        if (!playlist.isPresent()){
            return new ResponseEntity<>(new ResponMessage(Constant.PLAYLIST_NOT_FOUND), HttpStatus.OK);
        }
        return new ResponseEntity<>(playlistService.findVideoListByPlaylistId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createPlaylist(
            @RequestBody PlaylistDTO playlistDTO) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null) {
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Playlist playlist = new Playlist();
        playlist.setName(playlistDTO.getName());
        playlist.setUser(user);
        playlistService.save(playlist);
        return new ResponseEntity<>(new ResponMessage(Constant.CREATE_SUCCESS), HttpStatus.OK);
    }

    @PutMapping("{pl}/{vd}")
    public ResponseEntity<?> addOrRemoveVideo(
            @PathVariable Long pl,
            @PathVariable Long vd) {
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null){
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Optional<Playlist> playlist = playlistService.findByIdAndStatusIsTrueAndUserId(pl, user.getId());
        if (!playlist.isPresent()){
            return new ResponseEntity<>(new ResponMessage(Constant.PLAYLIST_NOT_FOUND), HttpStatus.OK);
        }
        List<Video> videoList = playlist.get().getVideoList();
        Optional<Video> video = videoService.findByIdAndStatusIsTrue(vd);
        if (!video.isPresent()){
            return new ResponseEntity<>(new ResponMessage(Constant.VIDEO_NOT_FOUND), HttpStatus.OK);
        }
        Optional<Video> checkVideo = playlistService.findVideoByPlaylist(vd,pl);
        if (checkVideo.isPresent()){
            videoList.remove(checkVideo.get());
            playlist.get().setVideoList(videoList);
            playlistService.save(playlist.get());
            return new ResponseEntity<>(new ResponMessage(Constant.REMOVE_SUCCESSFUL), HttpStatus.OK);
        }
        videoList.add(video.get());
        playlist.get().setVideoList(videoList);
        playlistService.save(playlist.get());
        return new ResponseEntity<>(new ResponMessage(Constant.ADD_SUCCESSFUL), HttpStatus.OK);
    }

}
