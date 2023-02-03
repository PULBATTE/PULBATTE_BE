package com.pulbatte.pulbatte.plantJournal.service;


import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.S3Uploader;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.plantJournal.dto.MyPlantManagementDTO;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalAddRequestDto;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalsRequestDto;
import com.pulbatte.pulbatte.plantJournal.entity.*;
import com.pulbatte.pulbatte.plantJournal.repository.*;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantJournalService {
    private final PlantJournalRepository plantJournalRepository;
    private final S3Uploader s3Uploader;
    private final DdayClickRepository ddayClickRepository;


    // 식물 일지 식물 등록
    @Transactional
    public void CreatePlantJournal(User user, PlantJournalAddRequestDto plantJournalAddRequestDto, MultipartFile multipartFile) throws IOException {
        String image = null;
        if (!multipartFile.isEmpty()) {                                      // 이미지 파일이 존재 할 경우
            image = s3Uploader.upload(multipartFile, "static");      // s3이미지 업로드
        } else {
            image = "https://d3usc6dqsfeh3v.cloudfront.net/post/noimage.png";
        }
        int WaterDDay, nutritionDDay, repottingDDay;
        WaterDDay = plantJournalAddRequestDto.getWaterCycle();
        nutritionDDay = plantJournalAddRequestDto.getNutritionCycle();
        repottingDDay = plantJournalAddRequestDto.getRepottingCycle();
        PlantJournal plantJournal = new PlantJournal(plantJournalAddRequestDto, user, image, WaterDDay, nutritionDDay, repottingDDay);
        plantJournalRepository.save(plantJournal);
    }

    // 식물 일지 전체 조회
    public List<PlantJournalsRequestDto> GetPlantJournalList(User user) {
        List<PlantJournal> PlansJournalsList = plantJournalRepository.findAllByUser(user);
        List<PlantJournalsRequestDto> plantJournalsRequestDtoList = new ArrayList<>();
        for (PlantJournal plantJournal : PlansJournalsList) {
            String image = plantJournal.getImage();
            image = "https://d1uh8s8qiogb97.cloudfront." + image.split(".cloudfront.")[1];
            plantJournalsRequestDtoList.add(new PlantJournalsRequestDto(plantJournal, image));
        }
        return plantJournalsRequestDtoList;
    }

    // 내 식물 관리하기 식물 상세 조회
    public MyPlantManagementDTO GetPlantJournal(User user, Long plantJournalId) {
        return new MyPlantManagementDTO(plantJournalRepository.findByUserAndId(user, plantJournalId));
    }

    // Dday 카테고리 클릭
    @Transactional
    public MsgResponseDto ClickDday(User user, Long plantJournalId, String clicktag) {
        PlantJournal plantJournal = plantJournalRepository.findByUserAndId(user, plantJournalId);
        if (clicktag.equals("water")) {
            if (plantJournal.getWaterDDay() != 0) {
                throw new CustomException(ErrorCode.NO_DDAY);
            }
            plantJournal.WaterClick(ddayClickRepository.countAllByUserAndPlantJournalAndClickTag(user, plantJournal, clicktag));            // 해당 식물 DB에 카운트 횟수 저장
        } else if (clicktag.equals("nutrition")) {
            if (plantJournal.getNutritionDDay() != 0) {
                throw new CustomException(ErrorCode.NO_DDAY);
            }
            plantJournal.NutritionClick(ddayClickRepository.countAllByUserAndPlantJournalAndClickTag(user, plantJournal, clicktag));
        } else if (clicktag.equals("repotting")) {
            if (plantJournal.getRepottingDDay() != 0) {
                throw new CustomException(ErrorCode.NO_DDAY);
            }
            plantJournal.RepottingClick(ddayClickRepository.countAllByUserAndPlantJournalAndClickTag(user, plantJournal, clicktag));
        } else {
            throw new CustomException(ErrorCode.NO_EXIST_CLICKTAG);
        }
        if (ddayClickRepository.findByLocalDateAndUserAndPlantJournalAndClickTag(java.time.LocalDate.now(), user, plantJournal, clicktag).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_DDAY_CLICK);
        }
        ddayClickRepository.save(new DdayClick(user, plantJournal, clicktag, java.time.LocalDate.now()));
        return new MsgResponseDto(SuccessCode.DDAY_CLICK_OK);
    }
}
