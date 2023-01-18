package com.pulbatte.pulbatte.plantSearch.dto;

import com.pulbatte.pulbatte.plantSearch.entity.Plant;
import com.pulbatte.pulbatte.plantSearch.entity.PlantTag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantDetailDto {
    private String plantName;           // 식물 이름
    private int beginner;
    private String content;         // 식물 설명
    private PlantTag plantTag;          // 식물 태그
    private String image;           // 이미지 url
    private String waterType;           // 물 주기
    private String water;           // 물 주기 상세 내용
    private String sunshineType;            // 햇빛 조건
    private String sunshine;            // 햇빛 조건 상세 내용
    private String humidityType;            // 습도 조건
    private String humidity;            // 습도 조건 상세 내용
    private String tempType;            // 온도 조건
    private String temp;            // 온도 조건 상세 내용

    @QueryProjection            // Q클래스 생성
    @Builder
    public PlantDetailDto(Plant plant) {
        this.plantName = plant.getPlantName();
        this.beginner = plant.getBeginner();
        this.content = plant.getContent();
        this.plantTag = plant.getPlantTag();
        this.image = plant.getImage();
        this.waterType = plant.getWaterType();
        this.water = plant.getWater();
        this.sunshineType = plant.getSunshineType();
        this.sunshine = plant.getSunshine();
        this.humidityType = plant.getHumidityType();
        this.humidity = plant.getHumidity();
        this.tempType = plant.getTempType();
        this.temp = plant.getTemp();
    }
}
