package com.example.demo.controller;

import com.example.demo.constant.Constant;
import com.example.demo.dto.request.PlaylistDTO;
import com.example.demo.dto.response.ResponMessage;
import com.example.demo.model.Playlist;
import com.example.demo.model.User;
import com.example.demo.security.userprincal.UserDetailService;
import com.example.demo.service.playlist.IPlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("playlist")
@CrossOrigin(origins = "*")
public class PlaylistController {
    @Autowired
    private UserDetailService userDetailService;
    @Autowired
    private IPlaylistService playlistService;
    @GetMapping
    public ResponseEntity<?> showAllPlaylist(){
        return new ResponseEntity<>(playlistService.findAll(), HttpStatus.OK);
    }
    @PostMapping
    private ResponseEntity<?> createPlaylist(@RequestBody PlaylistDTO playlistDTO){
        User user = userDetailService.getCurrentUser();
        if (user.getId() == null){
            return new ResponseEntity<>(new ResponMessage(Constant.NOT_LOGIN), HttpStatus.OK);
        }
        Playlist playlist = new Playlist();
        playlist.setPName(playlistDTO.getPName());
        playlist.setUser(user);
        playlistService.save(playlist);
        return new ResponseEntity<>(new ResponMessage(Constant.CREATE_SUCCESS), HttpStatus.OK);
    }
}
