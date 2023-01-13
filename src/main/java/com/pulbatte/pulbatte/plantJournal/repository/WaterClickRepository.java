package com.pulbatte.pulbatte.plantJournal.repository;

import com.pulbatte.pulbatte.plantJournal.entity.PlantJournal;
import com.pulbatte.pulbatte.plantJournal.entity.WaterClick;
import com.pulbatte.pulbatte.user.entity.User;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaterClickRepository extends JpaRepository<WaterClick, Long> {
    Optional<WaterClick> findByLocalDateAndUserAndPlantJournal (LocalDate localDate, User user, PlantJournal plantJournal);

    int countAllByUserAndPlantJournal (User user, PlantJournal plantJournal);
}
