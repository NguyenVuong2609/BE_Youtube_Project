package com.example.demo.service.playlist;

import com.example.demo.model.Playlist;
import com.example.demo.model.Video;
import com.example.demo.repository.IPlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PlaylistServiceImpl implements IPlaylistService{
    @Autowired
    private IPlaylistRepository playlistRepository;
    @Override
    public Iterable<Playlist> findAll() {
        return playlistRepository.findAll();
    }

    @Override
    public Optional<Playlist> findById(Long id) {
        return playlistRepository.findById(id);
    }

    @Override
    public void save(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public void deleteById(Long id) {
        playlistRepository.deleteById(id);
    }

    @Override
    public Optional<Playlist> findByIdAndStatusIsTrueAndUserId(Long plId, Long uId) {
        return playlistRepository.findByIdAndStatusIsTrueAndUserId(plId,uId);
    }

    @Override
    public Optional<Video> findVideoByPlaylist(Long vId, Long plId) {
        return playlistRepository.findVideoByPlaylist(vId,plId);
    }

    @Override
    public Optional<Playlist> findByIdAndStatusIsTrue(Long id) {
        return playlistRepository.findByIdAndStatusIsTrue(id);
    }

    @Override
    public Iterable<Playlist> findByUserId(Long id) {
        return playlistRepository.findByUserId(id);
    }

    @Override
    public Iterable<Video> findVideoListByPlaylistId(Long id) {
        return playlistRepository.findVideoListByPlaylistId(id);
    }
}
