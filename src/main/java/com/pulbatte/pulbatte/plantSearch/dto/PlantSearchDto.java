package com.pulbatte.pulbatte.plantSearch.dto;

import com.pulbatte.pulbatte.plant.entity.Plant;
import com.pulbatte.pulbatte.plant.entity.PlantTag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class PlantSearchDto {

    private Long id;
    private String plantName;
    private PlantTag plantTag;
    private String image;

    @QueryProjection            // Q파일 생성
    public PlantSearchDto(Long id, String plantName, PlantTag tag, String image) {
        this.id = id;
        this.plantName = plantName;
        this.plantTag = tag;
        this.image = image;
    }
}
