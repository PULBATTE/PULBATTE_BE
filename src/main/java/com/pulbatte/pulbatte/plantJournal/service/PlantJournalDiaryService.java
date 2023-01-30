package com.pulbatte.pulbatte.plantJournal.service;

import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.plantJournal.dto.CalendarResponseDto;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalDiaryRequestDto;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalDiaryResponseDto;
import com.pulbatte.pulbatte.plantJournal.entity.DdayClick;
import com.pulbatte.pulbatte.plantJournal.entity.PlantJournal;
import com.pulbatte.pulbatte.plantJournal.entity.PlantJournalDiary;
import com.pulbatte.pulbatte.plantJournal.repository.DdayClickRepository;
import com.pulbatte.pulbatte.plantJournal.repository.PlantJournalDiaryRepository;
import com.pulbatte.pulbatte.plantJournal.repository.PlantJournalRepository;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantJournalDiaryService {
    private final PlantJournalRepository plantJournalRepository;
    private final PlantJournalDiaryRepository plantJournalDiaryRepository;
    private final DdayClickRepository ddayClickRepository;

    // 식물 일지 일기 추가
    public void CreatePlantJournalDiary(User user, PlantJournalDiaryRequestDto plantJournalDiaryRequestDto, Long plantJournalId) {
        PlantJournal plantJournal = plantJournalRepository.findById(plantJournalId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_PLANT_JOURNAL_FOUND)
        );
        plantJournalDiaryRepository.save(new PlantJournalDiary(plantJournalDiaryRequestDto, user, plantJournal));
    }

    // 식물 일지 다이어리 상세 조회
    public PlantJournalDiaryResponseDto GetPlantJournalDiary(User user, Long plantJournalId, Long plantjournaldiaryid) {
        PlantJournalDiary plantJournalDiary = plantJournalDiaryRepository.findByUserAndPlantJournalIdAndId(user, plantJournalId, plantjournaldiaryid);

        return new PlantJournalDiaryResponseDto(plantJournalDiary);
    }

    // 식물 일지 다이어리 리스트
    public List<PlantJournalDiaryResponseDto> GetPlantJournalDiaryList(User user, Long plantjournalid) {
        List<PlantJournalDiary> plantJournalDiaryList = plantJournalDiaryRepository.findAllByUserIdAndPlantJournalId(user.getId(), plantjournalid);
        List<PlantJournalDiaryResponseDto> plantJournalDiaryResponseDtoList = new ArrayList<>();
        for (PlantJournalDiary plantJournalDiary : plantJournalDiaryList) {
            plantJournalDiaryResponseDtoList.add(new PlantJournalDiaryResponseDto(plantJournalDiary));
        }
        return plantJournalDiaryResponseDtoList;
    }

    // 식물 일지 다이어리 수정
    @Transactional
    public void UpdatePlantJournalDiary(User user, Long plantjournalid, Long plantjournaldiaryid, PlantJournalDiaryRequestDto plantJournalDiaryRequestDto) {
        PlantJournalDiary plantJournalDiary = plantJournalDiaryRepository.findByUserAndPlantJournalIdAndId(user, plantjournalid, plantjournaldiaryid);
        plantJournalDiary.update(plantJournalDiaryRequestDto.getContent());
    }

    // 식물 일지 다이어리 삭제
    @Transactional
    public void DeletePlantJournalDiary(User user, Long plantjournalid, Long plantjournaldiaryid) {
        PlantJournalDiary plantJournalDiary = plantJournalDiaryRepository.findByUserAndPlantJournalIdAndId(user, plantjournalid, plantjournaldiaryid);
        plantJournalDiaryRepository.delete(plantJournalDiary);
    }

    @Transactional
    public List<CalendarResponseDto> GetCalendar(User user, Long plantjournalid) {
//        List<DdayClick> ddayClicks = ddayClickRepository.findAllByUserIdAndPlantJournalId(user.getId(),plantjournalid);
        List<DdayClick> ddayClicks = ddayClickRepository.findAllByPlantJournalId(plantjournalid);
        List<CalendarResponseDto> calendarResponseDtoList = new ArrayList<>();
        for (DdayClick ddayClick : ddayClicks) {
            int water = 0;
            int nutrition = 0;
            int repot = 0;
            boolean cheak = false;
            for (CalendarResponseDto calendarResponseDto : calendarResponseDtoList) {
                if (calendarResponseDto.getLocalDate().equals(ddayClick.getLocalDate())) {
                    cheak = true;
                    System.out.println(ddayClick.getClickTag());
                    if (ddayClick.getClickTag().equals("water")) {
                        calendarResponseDto.update(calendarResponseDto.getWater() + 1, calendarResponseDto.getRepot(), calendarResponseDto.getNutrition());
                    } else if (ddayClick.getClickTag().equals("nutrition")) {
                        calendarResponseDto.update(calendarResponseDto.getWater(), calendarResponseDto.getRepot(), calendarResponseDto.getNutrition() + 1);
                    } else {
                        calendarResponseDto.update(calendarResponseDto.getWater(), calendarResponseDto.getRepot() + 1, calendarResponseDto.getNutrition());
                    }
                }
            }
            if (!cheak) {
                if (ddayClick.getClickTag().equals("water")) {
                    calendarResponseDtoList.add(new CalendarResponseDto(ddayClick.getLocalDate(), water+1, repot, nutrition));
                } else if (ddayClick.getClickTag().equals("nutrition")) {
                    calendarResponseDtoList.add(new CalendarResponseDto(ddayClick.getLocalDate(), water, repot, nutrition+1));
                } else {
                    calendarResponseDtoList.add(new CalendarResponseDto(ddayClick.getLocalDate(), water, repot+1, nutrition));
                }
            }
        }


        return calendarResponseDtoList;
    }
}
