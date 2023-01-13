package com.pulbatte.pulbatte.plantSearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlantListResponseDto {

    private List<PlantListDto> plantList = new ArrayList<>();

    @Builder
    public PlantListResponseDto(List<PlantListDto> plants) {
        this.plantList = plants;
//                .stream().map(PlantListDto::new).collect(Collectors.toList());
    }
}
