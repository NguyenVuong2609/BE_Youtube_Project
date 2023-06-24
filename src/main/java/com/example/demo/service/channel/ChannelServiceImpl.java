package com.example.demo.service.channel;

import com.example.demo.model.Channel;
import com.example.demo.model.User;
import com.example.demo.repository.IChannelRepository;
import com.example.demo.security.userprincal.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChannelServiceImpl implements IChannelService {
    @Autowired
    private IChannelRepository channelRepository;
    @Autowired
    private UserDetailService userDetailService;

    @Override
    public Iterable<Channel> findAll() {
        return channelRepository.findAll();
    }

    @Override
    public Optional<Channel> findById(Long id) {
        return channelRepository.findById(id);
    }

    @Override
    public void save(Channel channel) {
        channelRepository.save(channel);
    }

    @Override
    public void deleteById(Long id) {
        channelRepository.deleteById(id);
    }

    @Override
    public boolean existsByCh_name(String name) {
        return channelRepository.existsByChNameAndStatusIsTrue(name);
    }

    @Override
    public boolean existsByUserId(Long id) {
        return channelRepository.existsByUserIdAndStatusIsTrue(id);
    }

    @Override
    public Optional<Channel> findByUserIdAndStatusIsTrue(Long id) {
        return channelRepository.findByUserIdAndStatusIsTrue(id);
    }

    @Override
    public Optional<Channel> findByIdAndStatusIsTrue(Long id) {
        return channelRepository.findByIdAndStatusIsTrue(id);
    }

    @Override
    public boolean existsByIdAndUserIdAndStatusIsTrue(Long chId, Long uId) {
        return channelRepository.existsByIdAndUserIdAndStatusIsTrue(chId,uId);
    }

    @Override
    public Optional<User> findUserByChannel(Long flId, Long chId) {
        return channelRepository.findUserByChannel(flId,chId);
    }
}
