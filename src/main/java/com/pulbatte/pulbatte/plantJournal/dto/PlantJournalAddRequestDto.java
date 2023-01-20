package com.pulbatte.pulbatte.plantJournal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantJournalAddRequestDto {

    private String plantName; // 식물 이름
    private int waterCycle; // 물 주기 사이클
    private int nutritionCycle; // 영양 주기 사이클
    private int repottingCycle; // 분갈이 주기 사이클
    private int selectWater; // 사용자 선택 물량
    private int selectSunshine; // 사용자 선택 일조량
    private int selectWind; // 사용자 선택 통풍
}
