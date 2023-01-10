package com.pulbatte.pulbatte.bignner.repository;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeginnerRepository extends JpaRepository<Beginner, Long> {

    Optional<Beginner> findByBeginnerPlantName(String name);

}
