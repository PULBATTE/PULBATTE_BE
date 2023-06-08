package com.pulbatte.pulbatte.plantSearch.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "plant")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String plantName;               //식물 이름

    @Column
    private int beginner;
    @Column
    private String holder;

    @Column
    private String content;                 //식물 설명

    @Convert(converter = PlantTagConverter.class)
    @Column
    private PlantTag plantTag;                //식물 태그

    @Column
    private String image;                   //이미지

    @Column
    private String waterType;           //물 주기

    @Column
    private String water;                   //물 주기 상세 내용

    @Column
    private String sunshineType;            // 햇빛 조건

    @Column
    private String sunshine;                // 햇빛 조건 상세 내용


    @Column
    private String humidityType;            // 습도 조건

    @Column
    private String humidity;                // 습도 조건 상세 내용

    @Column
    private String tempType;            // 온도 조건

    @Column
    private String temp;                    // 온도 조건 상세 내용
}
