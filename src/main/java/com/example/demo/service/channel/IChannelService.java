package com.example.demo.service.channel;

import com.example.demo.model.Channel;
import com.example.demo.model.User;
import com.example.demo.service.IGenericService;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IChannelService extends IGenericService<Channel> {
    boolean existsByCh_name(String name);
    boolean existsByUserId(Long id);
    Optional<Channel> findByUserIdAndStatusIsTrue(Long id);
    Optional<Channel> findByIdAndStatusIsTrue(Long id);
    boolean existsByIdAndUserIdAndStatusIsTrue(Long chId, Long uId);
    Optional<User> findUserByChannel(
            @Param("flId") Long flId,
            @Param("chId") Long chId);
}
