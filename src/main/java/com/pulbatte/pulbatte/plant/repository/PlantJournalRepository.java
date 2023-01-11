package com.pulbatte.pulbatte.plant.repository;

import com.pulbatte.pulbatte.plant.entity.PlantJournal;
import com.pulbatte.pulbatte.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantJournalRepository extends JpaRepository<PlantJournal, Long> {
        List<PlantJournal> findAllByUser (User user);
        PlantJournal findByUserAndId (User user, Long id);
}
