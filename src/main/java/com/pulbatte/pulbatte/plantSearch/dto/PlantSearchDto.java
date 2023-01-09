package com.pulbatte.pulbatte.plantSearch.dto;

import com.pulbatte.pulbatte.plant.entity.PlantTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
@NoArgsConstructor
public class PlantSearchDto {
    private String plantName;

    private PlantTag plantTag;

}
