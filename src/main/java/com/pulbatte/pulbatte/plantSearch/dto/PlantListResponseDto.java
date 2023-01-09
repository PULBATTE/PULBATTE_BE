package com.pulbatte.pulbatte.plantSearch.dto;

import com.pulbatte.pulbatte.plant.entity.Plant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
