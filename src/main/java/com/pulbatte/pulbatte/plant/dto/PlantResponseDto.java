package com.pulbatte.pulbatte.plant.dto;

import com.pulbatte.pulbatte.plant.entity.Plant;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantResponseDto {
    private String plantName;
    private boolean beginner;
    private String content;
    private String plantTag;
    private String image;
    private String water;
    private String sunshine;
    private String humidity;
    private String temp;

    public PlantResponseDto(Plant plant){
        this.plantName = plant.getPlantName();
        this.beginner = plant.isBeginner();
        this.content = plant.getContent();
        this.plantTag = plant.getPlantTag();
        this.image = plant.getImage();
        this.water = plant.getWater();
        this.sunshine = plant.getSunshine();
        this.humidity = plant.getHumidity();
        this.temp = plant.getTemp();
    }
}
