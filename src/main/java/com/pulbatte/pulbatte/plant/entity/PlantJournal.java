package com.pulbatte.pulbatte.plant.entity;

import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "plantjournal")
@Getter
@NoArgsConstructor
public class PlantJournal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private User user;

    @Column
    private String plantName; // 식물 이름
    @Column
    private String image; // 식물 사진
    @Column
    private int waterCycle; // 물 주기 사이클
    @Column
    private LocalDateTime LastWateringDay; // 마지막 물 준 날
    @Column
    private int waterDDay; // 물주기 디데이
    @Column
    private int nutritionCycle; // 영양 주기 사이클
//    @Column
//    private LocalDateTime LastFertilizeDay; // 마지막 거름 준 날
    @Column
    private int nutritionDDay; // 영양 디데이
    @Column
    private int repottingCycle; // 분갈이 주기 사이클
//    @Column
//    private LocalDateTime LastRepottingDay; // 마지막 분갈이 한 날
    @Column
    private int repottingDDay; // 분갈이 디데이
    @Column
    private int selectWater; // 사용자 선택 물량
    @Column
    private int selcetSunshine; // 사용자 선택 일조량
    @Column
    private int selcetWind; // 사용자 선택 통풍




//    @Column
//    private int selcetStartTemp; // 사용자 선택 생육 온도
//    @Column
//    private int selcetEndTemp ; // 사용자 선택 생육 온도
//    @Column
//    private LocalDateTime firstDayWithPlants; // 식물과 처음 함께 한 날
}
