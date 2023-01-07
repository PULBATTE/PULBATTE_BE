package com.pulbatte.pulbatte.plant.dto;

import com.pulbatte.pulbatte.plant.entity.Plant;
import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantResponseDto {
    private String plantName;
    private PlantTag plantTag;
    private int beginner;
    private String content;
    private String image;
    private String waterType;
    private String water;
    private String sunshineType;

    private String sunshine;
    private String humidityType;
    private String humidity;
    private String tempType;
    private String temp;

    @QueryProjection
    public PlantResponseDto(Plant plant){
        this.plantName = plant.getPlantName();
        this.plantTag = plant.getPlantTag();
        this.beginner = plant.getBeginner();
        this.content = plant.getContent();
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
