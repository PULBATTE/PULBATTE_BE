package com.pulbatte.pulbatte.comment.repository;

import com.pulbatte.pulbatte.comment.entity.Comment;
import com.pulbatte.pulbatte.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long id, String userId);
    Long countByPostId(Long id);
    Optional<Comment> findByPostAndId(Post post, Long id);
}
