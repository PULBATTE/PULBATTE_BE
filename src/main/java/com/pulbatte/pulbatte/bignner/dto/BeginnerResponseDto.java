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
    private String tip;               // 식물 성장 팁
    private String image;                       // 이미지
    private int water;                          // 물양
    private int sunshine;                       // 일조량
    private int ventilation;                    // 통풍
    private List<BeginnerGraphResponseDto> beginnerGraph = new ArrayList<>();

    public BeginnerResponseDto(Beginner beginner){
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.tip = beginner.getTip();
        this.image = beginner.getImage();
        this.water = beginner.getWater();
        this.sunshine = beginner.getSunshine();
        this.ventilation = beginner.getVentilation();
    }
    public BeginnerResponseDto(BeginnerUser beginnerUser, List<BeginnerGraphResponseDto> beginnerGraphResponseDtoList){
        this.beginnerPlantName = beginnerUser.getBeginnerPlantName();
        this.tip = beginnerUser.getTip();
        this.image = beginnerUser.getImage();
        this.water = beginnerUser.getWater();
        this.sunshine = beginnerUser.getSunshine();
        this.ventilation = beginnerUser.getVentilation();
        this.beginnerGraph =beginnerGraphResponseDtoList;
    }

}