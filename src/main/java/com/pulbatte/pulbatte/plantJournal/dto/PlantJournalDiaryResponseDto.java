package com.pulbatte.pulbatte.plantJournal.dto;

import com.pulbatte.pulbatte.plantJournal.entity.PlantJournalDiary;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PlantJournalDiaryResponseDto {

    private Long plantJournalDiaryId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;



    public PlantJournalDiaryResponseDto(PlantJournalDiary plantJournalDiary){
        this.plantJournalDiaryId = plantJournalDiary.getId();
        this.content = plantJournalDiary.getContent();
        this.createdAt = plantJournalDiary.getCreatedAt();
        this.modifiedAt = plantJournalDiary.getModifiedAt();
    }
}
