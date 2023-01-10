package com.pulbatte.pulbatte.plant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DdayResponseDto {
    private int waterDDay; // 물주기 디데이
    private int nutritionDDay; // 영양 디데이
    private int repottingDDay; // 분갈이 디데이

    public DdayResponseDto(int waterDDay, int nutritionDDay, int repottingDDay){
        this.waterDDay = waterDDay;
        this.nutritionDDay = nutritionDDay;
        this.repottingDDay = repottingDDay;
    }
}
