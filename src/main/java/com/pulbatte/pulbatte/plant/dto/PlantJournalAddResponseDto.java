package com.pulbatte.pulbatte.plant.dto;

import com.pulbatte.pulbatte.plant.entity.PlantJournal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PlantJournalAddResponseDto {

    private Long id;
    private String plantName; // 식물 이름
    private String image; // 식물 사진
    private int waterCycle; // 물 주기 사이클
    private int nutritionCycle; // 영양 주기 사이클
    private int repottingCycle; // 분갈이 주기 사이클
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate LastWateringDay; // 마지막 물 준 날
    private int waterDDay; // 물주기 디데이
    private int nutritionDDay; // 영양 디데이
    private int repottingDDay; // 분갈이 디데이
    private int selectWater; // 사용자 선택 물량
    private int selcetSunshine; // 사용자 선택 일조량
    private int selcetWind; // 사용자 선택 통풍

    public PlantJournalAddResponseDto(PlantJournal plantJournal){
        this.id = plantJournal.getId();
        this.plantName = plantJournal.getPlantName();
        this.image = plantJournal.getImage();
        this.waterCycle = plantJournal.getWaterCycle();
        this.nutritionCycle = plantJournal.getNutritionCycle();
        this.repottingCycle = plantJournal.getRepottingCycle();
        this.LastWateringDay = plantJournal.getLastWateringDay();
        this.waterDDay = plantJournal.getWaterDDay();
        this.nutritionDDay = plantJournal.getNutritionDDay();
        this.repottingDDay = plantJournal.getRepottingDDay();
        this.selectWater = plantJournal.getSelectWater();
        this.selcetSunshine = plantJournal.getSelcetSunshine();
        this.selcetWind = plantJournal.getSelcetWind();
    }
}
