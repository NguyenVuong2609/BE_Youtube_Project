package com.example.demo.repository;

import com.example.demo.model.Channel;
import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IChannelRepository extends JpaRepository<Channel, Long> {
    boolean existsByChNameAndStatusIsTrue(String name);
    boolean existsByUserIdAndStatusIsTrue(Long id);
    Optional<Channel> findByUserIdAndStatusIsTrue(Long id);
    Optional<Channel> findByIdAndStatusIsTrue(Long id);
    boolean existsByIdAndUserIdAndStatusIsTrue(Long chId, Long uId);
    @Query("select distinct ch.followerList from Channel ch join ch.followerList fl where fl.id = :flId and ch.id = :chId")
    Optional<User> findFollowerByChannel(
            @Param("flId") Long flId,
            @Param("chId") Long chId);
    @Query("select ch.followerList from Channel ch where ch.id =:id")
    Iterable<User> findFollowerListByChannelId(@Param("id") Long id);
}
