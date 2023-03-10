package com.pulbatte.pulbatte.plantJournal.dto;

import com.pulbatte.pulbatte.plantSearch.entity.Plant;
import com.pulbatte.pulbatte.plantSearch.entity.PlantTag;
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
