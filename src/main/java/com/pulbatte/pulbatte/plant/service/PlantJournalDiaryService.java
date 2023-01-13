package com.pulbatte.pulbatte.plant.service;

import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.plant.dto.PlantJournalDiaryRequestDto;
import com.pulbatte.pulbatte.plant.dto.PlantJournalDiaryResponseDto;
import com.pulbatte.pulbatte.plant.entity.PlantJournal;
import com.pulbatte.pulbatte.plant.entity.PlantJournalDiary;
import com.pulbatte.pulbatte.plant.repository.*;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantJournalDiaryService {
    private final PlantJournalRepository plantJournalRepository;
    private final PlantJournalDiaryRepository plantJournalDiaryRepository;

    // 식물 일지 일기 추가
    public PlantJournalDiaryResponseDto CreatePlantJournalDiary(User user, PlantJournalDiaryRequestDto plantJournalDiaryRequestDto, Long plantJournalId) {
        PlantJournal plantJournal = plantJournalRepository.findById(plantJournalId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_PLANT_JOURNAL_FOUND)
        );
        PlantJournalDiary plantJournalDiary = plantJournalDiaryRepository.save(new PlantJournalDiary(plantJournalDiaryRequestDto,user,plantJournal));
        return new PlantJournalDiaryResponseDto(plantJournalDiary,plantJournalId,user.getId());
    }

    // 식물 일지 다이어리 상세 조회
    public PlantJournalDiaryResponseDto GetPlantJournalDiary(User user, Long plantJournalId, Long plantjournaldiaryid) {
        PlantJournalDiary plantJournalDiary = plantJournalDiaryRepository.findByUserAndPlantJournalIdAndId(user,plantJournalId,plantjournaldiaryid);

        return new PlantJournalDiaryResponseDto(plantJournalDiary,plantJournalId, user.getId());
    }

    // 식물 일지 다이어리 리스트
    public List<PlantJournalDiaryResponseDto> GetPlantJournalDiaryList(User user, Long plantJournalId) {
        List<PlantJournalDiary> plantJournalDiaryList = plantJournalDiaryRepository.findAllByPlantJournalId(plantJournalId);
        List<PlantJournalDiaryResponseDto> plantJournalDiaryResponseDtoList = new ArrayList<>();
        for(PlantJournalDiary plantJournalDiary : plantJournalDiaryList){
            plantJournalDiaryResponseDtoList.add(new PlantJournalDiaryResponseDto(plantJournalDiary,plantJournalId,user.getId()));
        }
        return plantJournalDiaryResponseDtoList;
    }
}
