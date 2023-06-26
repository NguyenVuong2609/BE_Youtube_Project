package com.example.demo.service.history;

import com.example.demo.model.History;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import com.example.demo.repository.IHistoryRepository;
import com.example.demo.security.userprincal.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HistoryServiceImpl implements IHistoryService{
    @Autowired
    private IHistoryRepository historyRepository;

    @Override
    public void save(History history) {
        historyRepository.save(history);
    }

    @Override
    public Optional<History> findById(Long id) {
        return historyRepository.findById(id);
    }

    @Override
    public Iterable<Video> getVideoListByHistoryId(Long id) {
        return historyRepository.getListVideo(id);
    }

    @Override
    public Optional<History> findByOwnerUsername(String username) {
        return historyRepository.findByOwnerUsername(username);
    }

    @Override
    public Optional<History> findByOwnerId(Long id) {
        return historyRepository.findByOwnerId(id);
    }

    @Override
    public Optional<Video> findVideoByHistory(Long hisId, Long vidId) {
        return historyRepository.findVideoByHistory(hisId,vidId);
    }


}
