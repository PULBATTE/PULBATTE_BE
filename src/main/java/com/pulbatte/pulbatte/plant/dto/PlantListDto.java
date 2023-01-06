package com.pulbatte.pulbatte.plant.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class PlantListDto {

    private String plantName;
    private String image;

    @QueryProjection
    public PlantListDto(String plantName, String image) {
        this.plantName = plantName;
        this.image = image;
    }
}
