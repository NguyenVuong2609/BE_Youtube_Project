package com.example.demo.service.playlist;

import com.example.demo.model.Playlist;
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
}
