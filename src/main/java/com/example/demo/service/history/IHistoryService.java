package com.example.demo.service.history;

import com.example.demo.model.History;
import com.example.demo.model.Video;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IHistoryService {
    void save(History history);
    Optional<History> findById(Long id);
    Iterable<Video> getVideoListByHistoryId(Long id);
    Optional<History> findByOwnerUsername(String username);
    Optional<History> findByOwnerId(Long id);
    Optional<Video> findVideoByHistory(Long hisId, Long vidId);
}
