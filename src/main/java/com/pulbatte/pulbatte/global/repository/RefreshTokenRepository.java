package com.pulbatte.pulbatte.global.repository;

import com.pulbatte.pulbatte.global.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository  extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByAccountUserId(String userId);
    Optional<RefreshToken> deleteByAccountUserId(String userId);
}