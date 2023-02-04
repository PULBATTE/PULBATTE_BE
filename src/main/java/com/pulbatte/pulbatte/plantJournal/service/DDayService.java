package com.pulbatte.pulbatte.plantJournal.service;

import com.pulbatte.pulbatte.alarm.dto.AlarmRequestDto;
import com.pulbatte.pulbatte.alarm.entity.AlarmType;
import com.pulbatte.pulbatte.alarm.service.SseService;
import com.pulbatte.pulbatte.plantJournal.entity.PlantJournal;
import com.pulbatte.pulbatte.plantJournal.repository.PlantJournalRepository;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class DDayService {
    private final PlantJournalRepository plantJournalRepository;
    private final SseService sseService;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")	// 매일 00시 정각
    public void Ddayschedule() {
        List<PlantJournal> plantJournalList = plantJournalRepository.findAll();
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

    @Transactional
    @Scheduled(cron = "0 0 9 * * ?")
    public ResponseEntity createDdayAlarm() {
        List<PlantJournal> plantJournalList = plantJournalRepository.findAll();
        List<User> targets = new ArrayList<>();

        for(PlantJournal journal : plantJournalList) {
            targets.add(journal.getUser());

            String url = "/api/plantjournal/" + journal.getId();
            String content = "";

            if(journal.getWaterDDay()==0) {
                content = "물주기 Dday 입니다.";
                AlarmRequestDto requestDto = AlarmRequestDto.builder()
                        .type(AlarmType.Dday)
                        .content(content)
                        .url(url)
                        .user(journal.getUser())
                        .build();
//                sseService.send(requestDto);
                eventPublisher.publishEvent(requestDto);
            }
            else if(journal.getNutritionDDay()==0) {
                content = "영양 주기 Dday 입니다.";
                AlarmRequestDto requestDto = AlarmRequestDto.builder()
                        .type(AlarmType.Dday)
                        .content(content)
                        .url(url)
                        .user(journal.getUser())
                        .build();
//                sseService.send(requestDto);
                eventPublisher.publishEvent(requestDto);
            }
            else if(journal.getRepottingDDay()==0) {
                content = "분갈이 Dday 입니다.";
                AlarmRequestDto requestDto = AlarmRequestDto.builder()
                        .type(AlarmType.Dday)
                        .content(content)
                        .url(url)
                        .user(journal.getUser())
                        .build();
//                sseService.send(requestDto);
                eventPublisher.publishEvent(requestDto);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
