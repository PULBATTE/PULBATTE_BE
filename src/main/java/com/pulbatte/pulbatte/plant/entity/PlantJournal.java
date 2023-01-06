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
    private String waterCycle; // 물 주기 사이클

    @Column
    private LocalDateTime LastWateringDay; // 마지막 물 준 날

    @Column
    private String fertilizerCycle; // 거름 주기 사이클

    @Column
    private LocalDateTime LastFertilizeDay; // 마지막 거름 준 날

    @Column
    private String repottingCycle; // 분갈이 주기 사이클

    @Column
    private LocalDateTime LastRepottingDay; // 마지막 분갈이 한 날

    @Column
    private String selcetSunshine; // 사용자 선택 일조량

    @Column
    private String selectWater; // 사용자 선택 물량

    @Column
    private Long selcetStartTemp; // 사용자 선택 생육 온도

    @Column
    private Long selcetEndTemp ; // 사용자 선택 생육 온도

    @Column
    private LocalDateTime firstDayWithPlants; // 식물과 처음 함께 한 날
}
