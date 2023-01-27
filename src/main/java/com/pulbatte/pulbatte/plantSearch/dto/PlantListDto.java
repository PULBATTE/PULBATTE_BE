package com.pulbatte.pulbatte.plantSearch.dto;

import com.pulbatte.pulbatte.plantSearch.entity.Plant;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantListDto {

    private Long id;
    private String plantName;

    private String plantTag;
    private int beginner;
    private String image;

    @QueryProjection            // Q파일 생성
    public PlantListDto(Plant plant) {
        this.id = plant.getId();
        this.plantName = plant.getPlantName();
        this.plantTag = plant.getPlantTag().toString();
        this.beginner = plant.getBeginner();
        this.image = plant.getImage();
    }
}
