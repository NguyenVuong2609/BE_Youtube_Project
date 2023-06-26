package com.example.demo.service.playlist;

import com.example.demo.model.Playlist;
import com.example.demo.model.Video;
import com.example.demo.service.IGenericService;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IPlaylistService extends IGenericService<Playlist> {
    Optional<Playlist> findByIdAndStatusIsTrueAndUserId(Long plId, Long uId);

    Optional<Video> findVideoByPlaylist(
            @Param("vId") Long vId,
            @Param("plId") Long plId);

    Optional<Playlist> findByIdAndStatusIsTrue(Long id);
    Iterable<Playlist> findByUserId(Long id);
    Iterable<Video> findVideoListByPlaylistId(Long id);
}
