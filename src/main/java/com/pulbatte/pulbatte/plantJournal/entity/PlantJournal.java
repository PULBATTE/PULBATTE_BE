package com.pulbatte.pulbatte.plantJournal.entity;

import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalAddRequestDto;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "plantjournal")
@Getter
@NoArgsConstructor
public class PlantJournal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private User user;
    @Column
    private String plantName;                       // 식물 이름
    @Column
    private String image;                           // 식물 사진
    @Column
    private int waterCycle;                         // 물 주기 사이클
    @Column
    private int nutritionCycle;                     // 영양 주기 사이클
    @Column
    private int repottingCycle;                     // 분갈이 주기 사이클
    @Column
    private int waterDDay;                          // 물주기 디데이
    @Column
    private int nutritionDDay;                      // 영양 디데이
    @Column
    private int repottingDDay;                      // 분갈이 디데이
    @Column
    private int totalWaterDDayClick;                //전체 물 주기 가능 횟수
    @Column
    private int totalNutritionDDayClick;            //전체 영양 주기 가능 횟수
    @Column
    private int totalRepottingDDayClick;            // 전체 분갈이 주기 가능 횟수
    @Column
    private int currentWaterDdayClick;              // 현재까지의 물 주기 클릭 횟수
    @Column
    private int currentNutritionDDayClick;          // 현재 까지의 영양 주기 클릭 횟수
    @Column
    private int currentRepottingDDayClick;          // 현재 까지의 분갈이 클릭 횟수
    @Column
    private int selectWater;                        // 사용자 선택 물량
    @Column
    private int selectSunshine;                     // 사용자 선택 일조량
    @Column
    private int selectWind;                         // 사용자 선택 통풍
    @Column
    private int withPlantDay;                       // 식물과 함께 한 날
    @Column
    private int waterCheck;                         // 물 Dday 눌렀는지 체크
    @Column
    private int nutritionCheck;                     // 영양 Dday 눌렀는지 체크
    @Column
    private int repottingCheck;                     // 분갈이 Dday 눌렀는지 체크

    @OneToMany(mappedBy = "plantJournal",cascade = CascadeType.REMOVE)
    private List<PlantJournalDiary> plantJournalDiaryList;

    @OneToMany(mappedBy = "plantJournal",cascade = CascadeType.REMOVE)
    private List<DdayClick> ddayClickList;

    public PlantJournal(PlantJournalAddRequestDto plantJournalAddRequestDto,User user, String image,int waterDDay,int nutritionDDay,int repottingDDay){
        this.user = user;
        this.plantName = plantJournalAddRequestDto.getPlantName();
        this.image = image;
        if(plantJournalAddRequestDto.getWaterCycle() <=0){
            this.waterCycle = 1;
        }else{
            this.waterCycle = plantJournalAddRequestDto.getWaterCycle();
        }
        if(plantJournalAddRequestDto.getNutritionCycle() <=0){
            this.nutritionCycle = 1;
        }else{
            this.nutritionCycle = plantJournalAddRequestDto.getNutritionCycle();
        }
        if(plantJournalAddRequestDto.getRepottingCycle() <=0){
            this.repottingCycle = 1;
        }else{
            this.repottingCycle = plantJournalAddRequestDto.getRepottingCycle();
        }
        this.waterDDay = waterDDay;
        this.nutritionDDay = nutritionDDay;
        this.repottingDDay = repottingDDay;
        this.selectWater = plantJournalAddRequestDto.getSelectWater();
        this.selectSunshine = plantJournalAddRequestDto.getSelectSunshine();
        this.selectWind = plantJournalAddRequestDto.getSelectWind();
        this.totalWaterDDayClick = 0;
        this.totalNutritionDDayClick = 0;
        this.totalRepottingDDayClick = 0;
        this.currentWaterDdayClick = 0;
        this.currentNutritionDDayClick = 0;
        this.currentRepottingDDayClick = 0;
        this.withPlantDay = 0;
        this.waterCheck = 0;
        this.repottingCheck = 0;
        this.nutritionCheck = 0;
    }

    public void Ddaymiuns(
            int waterDDay, int nutritionDDay, int repottingDDay, int withPlantDay,
            int totalWaterDdayClick,int totalNutritionDdayClick,int totalRepottingDdayClick){
        this.waterDDay = waterDDay;
        this.nutritionDDay = nutritionDDay;
        this.repottingDDay = repottingDDay;
        this.withPlantDay = withPlantDay;
        this.totalNutritionDDayClick = totalNutritionDdayClick;
        this.totalWaterDDayClick = totalWaterDdayClick;
        this.totalRepottingDDayClick = totalRepottingDdayClick;
        this.waterCheck = 0;
        this.repottingCheck = 0;
        this.nutritionCheck = 0;
    }
    public void WaterClick (int currentWaterDdayClick){
        this.currentWaterDdayClick = currentWaterDdayClick + 1;
        this.waterCheck = 1;
    }
    public void NutritionClick (int currentNutritionDDayClick){
        this.currentNutritionDDayClick = currentNutritionDDayClick + 1;
        this.nutritionCheck = 1;
    }
    public void RepottingClick (int currentRepottingDDayClick){
        this.currentRepottingDDayClick = currentRepottingDDayClick + 1;
        this.repottingCheck = 1;
    }
}