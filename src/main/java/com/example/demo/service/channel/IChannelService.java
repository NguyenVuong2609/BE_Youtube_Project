package com.example.demo.service.channel;

import com.example.demo.model.Channel;
import com.example.demo.service.IGenericService;

import java.util.Optional;

public interface IChannelService extends IGenericService<Channel> {
    boolean existsByCh_name(String name);
    boolean existsByUserId(Long id);
    Optional<Channel> findByUserIdAndStatusIsTrue(Long id);
    Optional<Channel> findByIdAndStatusIsTrue(Long id);
}
