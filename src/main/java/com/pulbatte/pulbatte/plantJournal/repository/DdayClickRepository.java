package com.pulbatte.pulbatte.plantJournal.repository;

import com.pulbatte.pulbatte.plantJournal.entity.DdayClick;
import com.pulbatte.pulbatte.plantJournal.entity.PlantJournal;
import com.pulbatte.pulbatte.user.entity.User;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DdayClickRepository extends JpaRepository<DdayClick, Long> {

    Optional<DdayClick> findByLocalDateAndUserAndPlantJournalAndClickTag(LocalDate localDate, User user, PlantJournal plantJournal,String clickTag);
    int countAllByUserAndPlantJournalAndClickTag(User user, PlantJournal plantJournal, String clickTag);
    List<DdayClick> findAllByUserIdAndPlantJournalId(Long id,Long plantjournalid);
    List<DdayClick> findAllByPlantJournalId(Long plantjournalid);
}
