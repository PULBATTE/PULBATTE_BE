package com.pulbatte.pulbatte.plantJournal.repository;

import com.pulbatte.pulbatte.plantJournal.entity.PlantJournalDiary;
import com.pulbatte.pulbatte.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantJournalDiaryRepository extends JpaRepository<PlantJournalDiary, Long> {


    List<PlantJournalDiary> findAllByUserIdAndPlantJournalIdOrderByCreatedAtDesc(Long UserId, Long plantJouranlId);
    PlantJournalDiary findByUserAndPlantJournalIdAndId(User user, Long plantJournalId, Long plantJournalDirayId);
}
