package com.pulbatte.pulbatte.bignner.dto;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Getter
@NoArgsConstructor
public class BeginnerResponseDto {
    private String beginnerPlantName;       //초보자용 식물
    private List<String> tipList;                     //식물 성장 팁
    private String image;                   //이미지
    private int water;                   //물양
    private int sunshine;                //일조량
    private int ventilation;                    //통풍

    public BeginnerResponseDto(Beginner beginner){
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.tipList = beginner.getTipList();
        this.image = beginner.getImage();
        this.water = beginner.getWater();
        this.sunshine = beginner.getSunshine();
        this.ventilation = beginner.getVentilation();
    }

}
