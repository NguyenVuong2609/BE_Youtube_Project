package com.example.demo.repository;

import com.example.demo.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IChannelRepository extends JpaRepository<Channel, Long> {
    boolean existsByChNameAndStatusIsTrue(String name);
    boolean existsByUserIdAndStatusIsTrue(Long id);
    Optional<Channel> findByUserIdAndStatusIsTrue(Long id);
    Optional<Channel> findByIdAndStatusIsTrue(Long id);
}
