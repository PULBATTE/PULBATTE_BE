package com.pulbatte.pulbatte.bignner.entity;

import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class BeginnerUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String beginnerPlantName;                       // 초보자용 식물
    @Column
    private String tip;                                     // 팁
    @Column
    private String image;                                   // 이미지
    @Column
    private int water;                                      // 물양
    @Column
    private int sunshine;                                   // 일조량
    @Column
    private int ventilation;                                // 온도
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "beginnerUser",cascade = CascadeType.REMOVE)
    private List<BeginnerGraph> beginnerGraphs = new ArrayList<>();

    public BeginnerUser(Beginner beginner, LocalDate startDate,User user){
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.tip = beginner.getTip();
        this.image = beginner.getImage();
        this.water = beginner.getWater();
        this.sunshine = beginner.getSunshine();
        this.ventilation = beginner.getVentilation();
        this.startDate = startDate;
        this.user = user;
    }


}