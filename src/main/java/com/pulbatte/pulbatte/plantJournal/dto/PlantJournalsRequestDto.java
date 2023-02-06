package com.pulbatte.pulbatte.plantJournal.dto;

import com.pulbatte.pulbatte.plantJournal.entity.PlantJournal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantJournalsRequestDto {

    private Long id;
    private String plantName;
    private String image;
    private int withPlantDay;

    public PlantJournalsRequestDto(PlantJournal plantJournal, String image){
        this.id = plantJournal.getId();
        this.plantName = plantJournal.getPlantName();
        this.image = image;
        this.withPlantDay = plantJournal.getWithPlantDay();
    }

    public PlantJournalsRequestDto(PlantJournal plantJournal) {
        this.id = plantJournal.getId();
        this.plantName = plantJournal.getPlantName();
        this.image = plantJournal.getImage();
        this.withPlantDay = plantJournal.getWithPlantDay();
    }
}
