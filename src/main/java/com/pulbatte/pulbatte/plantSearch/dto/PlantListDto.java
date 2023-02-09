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
    private String holder;

    private String plantTag;
    private String image;

    @QueryProjection            // Q파일 생성
    public PlantListDto(Plant plant) {
        this.id = plant.getId();
        this.plantName = plant.getPlantName();
        this.holder = plant.getHolder();
        this.plantTag = plant.getPlantTag().toString();
        this.image = plant.getImage();
    }
}
