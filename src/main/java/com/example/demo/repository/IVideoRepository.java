package com.example.demo.repository;

import com.example.demo.model.Category;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVideoRepository extends JpaRepository<Video, Long> {
    Optional<Video> findByIdAndChannelId(Long vId, Long chId);

    Iterable<Video> findAllByChannelIdAndStatusIsTrue(Long id);

    Iterable<Video> findAllByStatusIsTrue();

    Page<Video> findAllByStatusIsTrue(Pageable pageable);

    Optional<Video> findByIdAndStatusIsTrue(Long id);

    @Query("select distinct v.likeList from Video v join v.likeList lk" +
           " where v.id = :id and lk.id = :uId")
    Optional<User> findUserLikeByVideoId(
            @Param("id") Long id,
            @Param("uId") Long uId);

    @Query("select v.commentList from Video v where v.id = :id")
    Iterable<Comment> findListCommentByVideoId(
            @Param("id") Long id);

    @Query("select v from Video v where v.status = true order by RAND()")
    Page<Video> showRandomVideoList(Pageable pageable);

    Iterable<Video> findByStatusIsTrueOrderByViewsDesc();

    Iterable<Video> findByNameContainsAndStatusIsTrue(String name);
    @Query("select cl from Video v join v.categoryList cl where v.id = :id")
    Iterable<Category> findListCategoryByVideoId(@Param("id") Long id);
    @Query("select v from Video v join v.categoryList cl where cl.id = :cId and v.id != :vId")
    Iterable<Video> findListVideoByCategory(@Param("cId") Long cId, @Param("vId") Long vId);
}
