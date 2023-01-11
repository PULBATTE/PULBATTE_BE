package com.pulbatte.pulbatte.plant.entity;

import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

import javax.persistence.*;

@Entity(name = "waterClick")
@Getter
@NoArgsConstructor
public class WaterClick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private User user;
    @ManyToOne
    @JoinColumn(name = "PlantJournal_ID")
    private PlantJournal plantJournal;
    @Column
    private LocalDate localDate;

    public WaterClick(User user, PlantJournal plantJournal){
        this.user = user;
        this.plantJournal = plantJournal;
        this.localDate = LocalDate.now();
    }
}
