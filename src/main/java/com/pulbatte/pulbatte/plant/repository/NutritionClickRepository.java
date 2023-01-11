package com.pulbatte.pulbatte.plant.repository;

import com.pulbatte.pulbatte.plant.entity.NutritionClick;
import com.pulbatte.pulbatte.plant.entity.PlantJournal;
import com.pulbatte.pulbatte.user.entity.User;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NutritionClickRepository extends JpaRepository<NutritionClick, Long> {

    Optional<NutritionClick> findByLocalDateAndUserAndPlantJournal(LocalDate localDate, User user, PlantJournal plantJournal);
    int countAllByUserAndPlantJournal (User user, PlantJournal plantJournal);
}
