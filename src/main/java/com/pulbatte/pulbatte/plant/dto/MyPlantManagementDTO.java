package com.pulbatte.pulbatte.plant.dto;

import com.pulbatte.pulbatte.plant.entity.Plant;
import com.pulbatte.pulbatte.plant.entity.PlantJournal;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPlantManagementDTO {
    private Long id;
    private String plantName;
    private String image;
    private int withPlantDay;                     // 사용자가 식물과 함께한 시간
    private int selectWater;                      // 사용자 선택 물량
    private int selcetSunshine;                   // 사용자 선택 일조량
    private int selcetWind;                       // 사용자 선택 통풍
    private int waterDDay;                        // 물주기 디데이
    private int nutritionDDay;                    // 영양 디데이
    private int repottingDDay;                    // 분갈이 디데이
    private int totalWaterDDayClick;              // 물 주기 총 가능 횟수
    private int totalNutritionDDayClick ;         // 영양 주기 총 가능 횟수
    private int totalRepottingDDayClick;          // 분갈이 총 가능 횟수
    private int currentWaterDdayClick;            // 물 주기 한 횟수
    private int currentNutritionDDayClick ;       // 영양 주기 한 횟수
    private int currentRepottingDDayClick ;       // 분갈이 한 횟수
    private int waterPercentage;                  // 물 주기 횟수 / 물 주기 총 가능 횟수 * 100
    private int nutritionPercentage;              // 영양제 주기 횟수 / 영양제 주기 총 가능 횟수 * 100
    private int repottingPercentage;              // 분갈이 횟수 / 분갈이 총 가능 횟수 * 100

    public MyPlantManagementDTO(PlantJournal plantJournal){
        this.id = plantJournal.getId();
        this.plantName = plantJournal.getPlantName();
        this.image = plantJournal.getImage();
        this.withPlantDay = plantJournal.getWithPlantDay();
        this.selectWater = plantJournal.getSelectWater();
        this.selcetSunshine = plantJournal.getSelcetSunshine();
        this.selcetWind = plantJournal.getSelcetWind();
        this.waterDDay = plantJournal.getWaterDDay();
        this.nutritionDDay = plantJournal.getNutritionDDay();
        this.repottingDDay = plantJournal.getRepottingDDay();
        this.totalWaterDDayClick = plantJournal.getTotalWaterDDayClick();
        this.totalNutritionDDayClick = plantJournal.getTotalNutritionDDayClick();
        this.totalRepottingDDayClick = plantJournal.getTotalRepottingDDayClick();
        this.currentWaterDdayClick = plantJournal.getCurrentWaterDdayClick();
        this.currentNutritionDDayClick = plantJournal.getCurrentNutritionDDayClick();
        this.currentRepottingDDayClick = plantJournal.getCurrentRepottingDDayClick();
        this.waterPercentage =(int) ((double) currentWaterDdayClick/totalWaterDDayClick*100);
        this.nutritionPercentage = (int)((double)currentNutritionDDayClick/totalNutritionDDayClick*100);
        this.repottingPercentage = (int)((double)currentRepottingDDayClick/totalRepottingDDayClick*100);
    }
}
