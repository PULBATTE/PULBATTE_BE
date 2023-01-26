package com.pulbatte.pulbatte.plantJournal.service;


import com.pulbatte.pulbatte.global.MsgResponseDto;
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
import org.joda.time.LocalDate;
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
        }else{
            image = "https://brighto8iz.s3.ap-northeast-2.amazonaws.com/plantTest/%EA%B8%B0%EB%B3%B8%EC%9D%B4%EB%AF%B8%EC%A7%80.png";
        }
        int WaterDDay = plantJournalAddRequestDto.getWaterCycle();
        int nutritionDDay = plantJournalAddRequestDto.getNutritionCycle();   // 영양 주기 Dday를 영양 Cycle로 지정
        int repottingDDay = plantJournalAddRequestDto.getRepottingCycle();   // 분갈이 주기 Dday를 분갈이 Cycle로 지정
        PlantJournal plantJournal = new PlantJournal(plantJournalAddRequestDto,user,image,WaterDDay,nutritionDDay,repottingDDay);
        plantJournalRepository.save(plantJournal);
//        return new PlantJournalAddResponseDto(plantJournal);
    }

    // 식물 일지 전체 조회
    public List<PlantJournalsRequestDto> GetPlantJournalList(User user) {
        List<PlantJournal> PlansJournalsList = plantJournalRepository.findAllByUser(user);
        List<PlantJournalsRequestDto> plantJournalsRequestDtoList = new ArrayList<>();
        for(PlantJournal plantJournal : PlansJournalsList){
            plantJournalsRequestDtoList.add(new PlantJournalsRequestDto(plantJournal));
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
        PlantJournal plantJournal = plantJournalRepository.findByUserAndId(user,plantJournalId);
        if(clicktag.equals("water")){
            if(plantJournal.getWaterDDay() !=0){
                throw new CustomException(ErrorCode.NO_DDAY);
            }
            plantJournal.WaterClick(ddayClickRepository.countAllByUserAndPlantJournalAndClickTag(user, plantJournal, clicktag));            // 해당 식물 DB에 카운트 횟수 저장
        }else if(clicktag.equals("nutrition")){
            if(plantJournal.getNutritionDDay() !=0){
                throw new CustomException(ErrorCode.NO_DDAY);
            }
            plantJournal.NutritionClick(ddayClickRepository.countAllByUserAndPlantJournalAndClickTag(user, plantJournal, clicktag));
        }else if(clicktag.equals("repotting")){
            if(plantJournal.getRepottingDDay() != 0){
                throw new CustomException(ErrorCode.NO_DDAY);
            }
            plantJournal.RepottingClick(ddayClickRepository.countAllByUserAndPlantJournalAndClickTag(user, plantJournal, clicktag));
        }else{
            throw new CustomException(ErrorCode.NO_EXIST_CLICKTAG);
        }
        if(ddayClickRepository.findByLocalDateAndUserAndPlantJournalAndClickTag(LocalDate.now(), user, plantJournal, clicktag).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_DDAY_CLICK);
        }
        ddayClickRepository.save(new DdayClick(user, plantJournal, clicktag));
        return new MsgResponseDto(SuccessCode.DDAY_CLICK_OK);
    }
}
