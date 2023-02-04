package com.pulbatte.pulbatte.plantJournal.entity;

import com.pulbatte.pulbatte.global.entity.TimeStamped;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalDiaryRequestDto;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column
    private LocalDateTime createdAt;



    public PlantJournalDiary(
            PlantJournalDiaryRequestDto plantJournalDiaryRequestDto, User user,
            PlantJournal plantJournal, LocalDateTime createdAt
    ){
        this.content = plantJournalDiaryRequestDto.getContent();
        this.user = user;
        this.plantJournal = plantJournal;
        this.createdAt = createdAt;
    }

    public void update(String content){
        this.content = content;
    }
}
