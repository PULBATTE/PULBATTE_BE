package com.pulbatte.pulbatte.plant.dto;

import com.pulbatte.pulbatte.plant.entity.PlantJournal;
import com.pulbatte.pulbatte.plant.entity.PlantJournalDiary;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PlantJournalDiaryResponseDto {
    private Long plantJournalId;
    private Long userId;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



    public PlantJournalDiaryResponseDto(PlantJournalDiary plantJournalDiary,Long plantJournalId, Long userId){
        this.plantJournalId = plantJournalId;
        this.userId = userId;
        this.content = plantJournalDiary.getContent();
        this.createdAt = plantJournalDiary.getCreatedAt();
        this.modifiedAt = plantJournalDiary.getModifiedAt();
    }
}
