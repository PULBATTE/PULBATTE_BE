package com.pulbatte.pulbatte.plantJournal.repository;

import com.pulbatte.pulbatte.plantSearch.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
