package com.pulbatte.pulbatte.bignner.dto;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.bignner.entity.BeginnerUser;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BeginnerResponseDto {
    private String beginnerPlantName;           // 초보자용 식물
    private List<String> tipList;               // 식물 성장 팁
    private String image;                       // 이미지
    private int water;                          // 물양
    private int sunshine;                       // 일조량
    private int ventilation;                    // 통풍
    private Boolean like;                       // 테스트 결과에 따른 추천
    private Boolean overlap;                    // 중복된 데이터인지 확인
    private String resultPlantString;
    private List<BeginnerGraphResponseDto> beginnerGraph = new ArrayList<>();

    public BeginnerResponseDto(Beginner beginner, Boolean like,Boolean overlap){
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.resultPlantString = beginner.getResultPlantString();
        this.image = beginner.getImage();
        this.water = beginner.getWater();
        this.sunshine = beginner.getSunshine();
        this.ventilation = beginner.getVentilation();
        this.like = like;
        this.overlap = overlap;
    }
    public BeginnerResponseDto(Beginner beginner, List<BeginnerGraphResponseDto> beginnerGraphRepositoryList){
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.tipList = beginner.getTipList();
        this.image = beginner.getImage();
        this.water = beginner.getWater();
        this.sunshine = beginner.getSunshine();
        this.ventilation = beginner.getVentilation();
        this.beginnerGraph = beginnerGraphRepositoryList;
    }

}