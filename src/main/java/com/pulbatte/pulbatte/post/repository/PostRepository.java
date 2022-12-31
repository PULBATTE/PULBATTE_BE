package com.pulbatte.pulbatte.post.repository;

import com.pulbatte.pulbatte.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post ,Long> {
    /*Page<Post> findAllByCategoryOrderByCreatedAtDesc(String interestTag, Pageable pageable);*/
    Optional<Post> findByIdAndNickname(Long id, String nickname);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
