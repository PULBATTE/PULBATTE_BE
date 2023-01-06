package com.pulbatte.pulbatte.bignner.dto;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
@Getter
@NoArgsConstructor
public class BeginnerResponseDto {
    private String beginnerPlantName;       //초보자용 식물
    private String tip;                     //식물 성장 팁
    private String image;                   //이미지
    private String water;                   //물양
    private String sunshine;                //일조량
    private String humidity;                //습도
    private String temp;                    //온도

    public BeginnerResponseDto(Beginner beginner){
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.tip = beginner.getTip();
        this.image = beginner.getImage();
        this.water = beginner.getWater();
        this.sunshine = beginner.getSunshine();
        this.humidity = beginner.getHumidity();
        this.temp = beginner.getTemp();
    }

}
