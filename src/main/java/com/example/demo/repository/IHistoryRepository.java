package com.example.demo.repository;

import com.example.demo.model.History;
import com.example.demo.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IHistoryRepository extends JpaRepository<History, Long> {
    @Query("select h.videoList from History h join h.videoList vl where h.id = :id and vl.status = true")
    Iterable<Video> getListVideo(
            @Param("id") Long id);

    Optional<History> findByOwnerUsername(String username);

    Optional<History> findByOwnerId(Long id);

    @Query("select distinct h.videoList from History h join h.videoList vl where vl.id = :vId and h.id = :hId")
    Optional<Video> findVideoByHistory(
            @Param("vId") Long vId,
            @Param("hId") Long hId);
}
