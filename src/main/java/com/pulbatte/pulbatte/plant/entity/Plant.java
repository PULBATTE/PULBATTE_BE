package com.pulbatte.pulbatte.plant.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "plant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String plantName;               //식물 이름

    @Column
    private int beginner;

    @Column
    private String content;                 //식물 설명

    @Enumerated(value = EnumType.STRING)
    @Column
    private PlantTag plantTag;                //식물 태그

    @Column
    private String image;                   //이미지

    @Column
    private String waterType;

    @Column
    private String water;                   //물양

    @Column
    private String sunshineType;

    @Column
    private String sunshine;                //일조량


    @Column
    private String humidityType;

    @Column
    private String humidity;                //습도

    @Column
    private String tempType;

    @Column
    private String temp;                    //온도
}
