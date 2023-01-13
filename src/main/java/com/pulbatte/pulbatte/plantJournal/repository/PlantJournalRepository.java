package com.pulbatte.pulbatte.plantJournal.repository;

import com.pulbatte.pulbatte.plantJournal.entity.PlantJournal;
import com.pulbatte.pulbatte.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantJournalRepository extends JpaRepository<PlantJournal, Long> {
        List<PlantJournal> findAllByUser (User user);
        PlantJournal findByUserAndId (User user, Long id);
}
