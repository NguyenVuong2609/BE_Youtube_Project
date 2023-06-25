package com.example.demo.repository;

import com.example.demo.model.Playlist;
import com.example.demo.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPlaylistRepository extends JpaRepository<Playlist, Long> {
    Optional<Playlist> findByIdAndStatusIsTrueAndUserId(Long plId, Long uId);
    @Query("select distinct pl.videoList from Playlist pl join pl.videoList vl where vl.id = :vId and pl.id = :plId")
    Optional<Video> findVideoByPlaylist(
            @Param("vId") Long vId,
            @Param("plId") Long plId);
    Optional<Playlist> findByIdAndStatusIsTrue(Long id);
}
