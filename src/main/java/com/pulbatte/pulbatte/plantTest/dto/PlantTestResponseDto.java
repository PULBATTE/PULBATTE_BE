package com.pulbatte.pulbatte.plantTest.dto;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.plantTest.entity.PlantTest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PlantTestResponseDto {
    private String beginnerPlantName;
    private List<String> testResult = new ArrayList<>();

    public PlantTestResponseDto(Beginner beginner, PlantTest plantTest){
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.testResult = plantTest.getResultString();
    }
}
