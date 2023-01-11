package com.pulbatte.pulbatte.plant.dto;

import com.pulbatte.pulbatte.plant.entity.PlantJournal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantJournalsRequestDto {

    private Long id;
    private String plantName;
    private String image;
    private int withPlantDay;

    public PlantJournalsRequestDto(PlantJournal plantJournal){
        this.id = plantJournal.getId();
        this.plantName = plantJournal.getPlantName();
        this.image = plantJournal.getImage();
        this.withPlantDay = plantJournal.getWithPlantDay();
    }

}
