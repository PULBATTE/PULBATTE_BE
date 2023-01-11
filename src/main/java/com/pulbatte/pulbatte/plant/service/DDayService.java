package com.pulbatte.pulbatte.plant.service;

import com.pulbatte.pulbatte.plant.entity.PlantJournal;
import com.pulbatte.pulbatte.plant.repository.PlantJournalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DDayService {
    private final PlantJournalRepository plantJournalRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")	// 매일 00시 정각
    public void Ddayschedule() {
        List<PlantJournal> plantJournalList = plantJournalRepository.findAll();
        log.info("12시 Schedul 시작 ");
        log.info("PlantJournalListCount : " + plantJournalList.size());
        for (PlantJournal plantJournal : plantJournalList){
            int water = plantJournal.getWaterDDay() - 1;
            int nutrition = plantJournal.getNutritionDDay() - 1;
            int repotting = plantJournal.getRepottingDDay() - 1;
            int withplantDay = plantJournal.getWithPlantDay() + 1;
            if(water < 0){
                water = plantJournal.getWaterCycle()-1;
            }
            if(nutrition < 0){
                nutrition = plantJournal.getNutritionCycle()-1;
            }
            if(repotting < 0){
                repotting = plantJournal.getRepottingCycle()-1;
            }
            int totalWaterDdayClick = withplantDay/plantJournal.getWaterCycle();
            int totalNutritionDdayClick = withplantDay/plantJournal.getNutritionCycle();
            int totalRepottingDdayClick = withplantDay/plantJournal.getRepottingCycle();
            plantJournal.Ddaymiuns(water, nutrition,repotting,withplantDay,totalWaterDdayClick,totalNutritionDdayClick,totalRepottingDdayClick);
        }

    }
}