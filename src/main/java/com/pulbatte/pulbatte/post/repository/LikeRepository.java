package com.pulbatte.pulbatte.post.repository;

import com.pulbatte.pulbatte.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUserId(Long postId , Long userId);
    void deleteByPostIdAndUserId(Long postId, Long userId);
    @Query(value = "select count(1) from post_like inner join post on post_like.post_id = post.id where post_like.post_id = :postId", nativeQuery = true)
    Long likeCnt(@PathVariable("id") Long postId);  //nativeQuery 를 사용하여 좋아요 개수 count
}
