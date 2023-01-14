package com.pulbatte.pulbatte.bignner.repository;

import com.pulbatte.pulbatte.bignner.entity.BeginnerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeginnerUserRepository extends JpaRepository<BeginnerUser,Long> {

    Optional<BeginnerUser> findByUserId(Long aLong);
}
