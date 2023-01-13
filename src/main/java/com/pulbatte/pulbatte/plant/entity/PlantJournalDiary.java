package com.pulbatte.pulbatte.plant.entity;

import com.pulbatte.pulbatte.global.entity.TimeStamped;
import com.pulbatte.pulbatte.plant.dto.PlantJournalDiaryRequestDto;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "plantjournaldiary")
@Getter
@NoArgsConstructor
public class PlantJournalDiary extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String content;
    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PLANTJOURNAL_ID")
    private PlantJournal plantJournal;

    public PlantJournalDiary(PlantJournalDiaryRequestDto plantJournalDiaryRequestDto, User user, PlantJournal plantJournal){
        this.content = plantJournalDiaryRequestDto.getContent();
        this.user = user;
        this.plantJournal = plantJournal;
    }
}
