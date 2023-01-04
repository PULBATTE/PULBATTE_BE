package com.pulbatte.pulbatte.plant.repository;

import com.pulbatte.pulbatte.plant.entity.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
