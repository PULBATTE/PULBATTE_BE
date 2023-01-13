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
    //테스트 결과 성격
    private String resultTitle;
    private String resultImage;
    private List<String> resultString = new ArrayList<>();
    //테스트 결과 식물
    private String resultPlantImage;
    private String beginnerPlantName;
    private List<String> resultPlantString = new ArrayList<>();

    public PlantTestResponseDto(Beginner beginner, PlantTest plantTest){

        this.resultTitle = plantTest.getResultTitle();
        this.resultImage = plantTest.getResultImage();
        this.resultString = plantTest.getResultString();
        this.resultPlantImage = plantTest.getResultPlantImage();
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.resultPlantString = plantTest .getResultPlantString();

    }
}
