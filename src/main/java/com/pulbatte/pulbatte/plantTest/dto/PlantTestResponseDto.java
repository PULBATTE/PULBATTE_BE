package com.pulbatte.pulbatte.plantTest.dto;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.plantTest.entity.TestResult;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class PlantTestResponseDto {
    //테스트 결과 성격
    private String resultTitle;
    private String resultImage;
    private String resultString;
    //테스트 결과 식물
    private String resultPlantImage;
    private String beginnerPlantName;
    private String resultPlantString;

    // 테스트 결과 출력
    public PlantTestResponseDto(Beginner beginner, TestResult testResult){
        this.resultTitle = testResult.getResultTitle();              // 결과 제목
        this.resultImage = testResult.getResultImage();              // 테스트 결과 성격 이미지
        this.resultString = testResult.getResultString();            // 테스트 결과 성격 멘트
        this.beginnerPlantName = beginner.getBeginnerPlantName();   // 테스트 결과 식물 이름
        this.resultPlantImage = beginner.getImage();                // 테스트 결과 식물 이미지
        this.resultPlantString = beginner.getResultPlantString();   // 테스트 결과 식물 멘트
    }
}
