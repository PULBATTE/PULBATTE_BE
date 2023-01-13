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

    // 테스트 결과 출력
    public PlantTestResponseDto(Beginner beginner, PlantTest plantTest){

        this.resultTitle = plantTest.getResultTitle();              // 결과 제목
        this.resultImage = plantTest.getResultImage();              // 테스트 결과 성격 이미지
        this.resultString = plantTest.getResultString();            // 테스트 결과 멘트
        this.resultPlantImage = plantTest.getResultPlantImage();    // 테스트 결과 식물 이미지
        this.beginnerPlantName = beginner.getBeginnerPlantName();   // 테스트 결과 식물 이름
        this.resultPlantString = plantTest .getResultPlantString(); // 테스트 결과 식물 멘트

    }
}
