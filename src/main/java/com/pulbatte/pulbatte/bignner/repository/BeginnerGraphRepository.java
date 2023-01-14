package com.pulbatte.pulbatte.bignner.repository;

import com.pulbatte.pulbatte.bignner.entity.BeginnerGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BeginnerGraphRepository extends JpaRepository<BeginnerGraph, Long> {
    Optional<BeginnerGraph> findByUserId(Long userId);
    Optional<BeginnerGraph> findByLocalDateAndUserId(LocalDate localDate,Long userId);
}