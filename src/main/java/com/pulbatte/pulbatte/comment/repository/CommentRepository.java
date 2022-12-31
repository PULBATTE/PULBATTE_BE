package com.pulbatte.pulbatte.comment.repository;

import com.pulbatte.pulbatte.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long id, String userId);
}
