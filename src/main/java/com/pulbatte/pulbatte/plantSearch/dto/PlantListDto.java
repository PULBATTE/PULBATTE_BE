package com.pulbatte.pulbatte.plantSearch.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantListDto {

    private Long id;
    private String plantName;
    private String image;

//    @QueryProjection            // Q파일 생성
    public PlantListDto(Long id, String plantName, String image) {
        this.id = id;
        this.plantName = plantName;
        this.image = image;
    }
}
