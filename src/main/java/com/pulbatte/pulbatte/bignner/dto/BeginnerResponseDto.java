package com.pulbatte.pulbatte.bignner.dto;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class BeginnerResponseDto {
    private String beginnerPlantName;           // 초보자용 식물
    private List<String> tipList;               // 식물 성장 팁
    private String image;                       // 이미지
    private int water;                          // 물양
    private int sunshine;                       // 일조량
    private int ventilation;                    // 통풍
    private Map<LocalDate,String> beginnerGraph;// 식집사 가이드 그래프

    public BeginnerResponseDto(Beginner beginner){
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.tipList = beginner.getTipList();
        this.image = beginner.getImage();
        this.water = beginner.getWater();
        this.sunshine = beginner.getSunshine();
        this.ventilation = beginner.getVentilation();
    }
    public BeginnerResponseDto(Beginner beginner, User user){
        this.beginnerPlantName = beginner.getBeginnerPlantName();
        this.tipList = beginner.getTipList();
        this.image = beginner.getImage();
        this.water = beginner.getWater();
        this.sunshine = beginner.getSunshine();
        this.ventilation = beginner.getVentilation();
    }

}
