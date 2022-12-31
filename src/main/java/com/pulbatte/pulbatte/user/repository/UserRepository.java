package com.pulbatte.pulbatte.user.repository;

import com.pulbatte.pulbatte.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String UserId);
    Optional<User> findByNickname(String nickname);
}
