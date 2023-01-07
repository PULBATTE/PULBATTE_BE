package com.pulbatte.pulbatte.plant.dto;

import com.pulbatte.pulbatte.plant.entity.Plant;
import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PlantSearchResponseDto {

    private Long id;
    private String plantName;
    private PlantTag plantTag;
    private String image;

    @QueryProjection
    public PlantSearchResponseDto(Plant plant) {
        this.id = plant.getId();
        this.plantName = plant.getPlantName();
        this.plantTag = plant.getPlantTag();
        this.image = plant.getImage();
    }
}
