package com.pulbatte.pulbatte.plant.repository;

import com.pulbatte.pulbatte.plant.entity.PlantJournalDiary;
import com.pulbatte.pulbatte.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantJournalDiaryRepository extends JpaRepository<PlantJournalDiary, Long> {

    List<PlantJournalDiary> findAllByUserId(Long UserId);
    PlantJournalDiary findByUserAndPlantJournalIdAndId(User user, Long plantJournalId, Long plantJournalDirayId);
}
