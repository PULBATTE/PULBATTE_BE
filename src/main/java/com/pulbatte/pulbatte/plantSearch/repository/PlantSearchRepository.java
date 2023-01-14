package com.pulbatte.pulbatte.plantSearch.repository;

import com.pulbatte.pulbatte.plantSearch.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantSearchRepository extends JpaRepository<Plant, Long> {
}
