package com.pulbatte.pulbatte.plantJournal.entity;

import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

import javax.persistence.*;

@Entity(name = "ddayClick")
@Getter
@NoArgsConstructor
public class DdayClick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private LocalDate localDate;
    @Column
    private String clickTag;
    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PlantJournal_ID")
    private PlantJournal plantJournal;

    public DdayClick(User user, PlantJournal plantJournal, String clickTag,LocalDate localDate){
        this.user = user;
        this.plantJournal = plantJournal;
        this.clickTag = clickTag;
        this.localDate = localDate;
    }
}
