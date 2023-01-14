package com.pulbatte.pulbatte.plantJournal.service;

import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalDiaryRequestDto;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalDiaryResponseDto;
import com.pulbatte.pulbatte.plantJournal.entity.PlantJournal;
import com.pulbatte.pulbatte.plantJournal.entity.PlantJournalDiary;
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

    // 식물 일지 일기 추가
    public void CreatePlantJournalDiary(User user, PlantJournalDiaryRequestDto plantJournalDiaryRequestDto, Long plantJournalId) {
        PlantJournal plantJournal = plantJournalRepository.findById(plantJournalId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_PLANT_JOURNAL_FOUND)
        );
        plantJournalDiaryRepository.save(new PlantJournalDiary(plantJournalDiaryRequestDto,user,plantJournal));
    }

    // 식물 일지 다이어리 상세 조회
    public PlantJournalDiaryResponseDto GetPlantJournalDiary(User user, Long plantJournalId, Long plantjournaldiaryid) {
        PlantJournalDiary plantJournalDiary = plantJournalDiaryRepository.findByUserAndPlantJournalIdAndId(user,plantJournalId,plantjournaldiaryid);

        return new PlantJournalDiaryResponseDto(plantJournalDiary);
    }

    // 식물 일지 다이어리 리스트
    public List<PlantJournalDiaryResponseDto> GetPlantJournalDiaryList(User user) {
        List<PlantJournalDiary> plantJournalDiaryList = plantJournalDiaryRepository.findAllByUserId(user.getId());
        List<PlantJournalDiaryResponseDto> plantJournalDiaryResponseDtoList = new ArrayList<>();
        for(PlantJournalDiary plantJournalDiary : plantJournalDiaryList){
            plantJournalDiaryResponseDtoList.add(new PlantJournalDiaryResponseDto(plantJournalDiary));
        }
        return plantJournalDiaryResponseDtoList;
    }

    // 식물 일지 다이어리 수정
    @Transactional
    public void UpdatePlantJournalDiary(User user, Long plantjournalid, Long plantjournaldiaryid, PlantJournalDiaryRequestDto plantJournalDiaryRequestDto) {
        PlantJournalDiary plantJournalDiary = plantJournalDiaryRepository.findByUserAndPlantJournalIdAndId(user,plantjournalid,plantjournaldiaryid);
        plantJournalDiary.update(plantJournalDiaryRequestDto.getContent());
    }

    // 식물 일지 다이어리 삭제
    @Transactional
    public void DeletePlantJournalDiary(User user, Long plantjournalid, Long plantjournaldiaryid) {
        PlantJournalDiary plantJournalDiary = plantJournalDiaryRepository.findByUserAndPlantJournalIdAndId(user,plantjournalid,plantjournaldiaryid);
        plantJournalDiaryRepository.delete(plantJournalDiary);
    }
}