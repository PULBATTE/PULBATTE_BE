package com.pulbatte.pulbatte.plant.service;


import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.S3Uploader;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.plant.dto.*;
import com.pulbatte.pulbatte.plant.entity.*;
import com.pulbatte.pulbatte.plant.repository.*;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import org.joda.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantJournalRepository plantJournalRepository;
    private final S3Uploader s3Uploader;
    private final WaterClickRepository waterClickRepository;
    private final RepottingCilckRepository repottingCilckRepository;
    private final NutritionClickRepository nutritionClickRepository;
    private final PlantJournalDiaryRepository plantJournalDiaryRepository;

    // 식물 일지 식물 등록
    @Transactional
    public PlantJournalAddResponseDto PlantJournalAdd(User user, PlantJournalAddRequestDto plantJournalAddRequestDto, MultipartFile multipartFile) throws IOException {
        String image = null;
        if (!multipartFile.isEmpty()) {                                      // 이미지 파일이 존재 할 경우
            image = s3Uploader.upload(multipartFile, "static");      // s3이미지 업로드
        }
        int WaterDDay = Period.between(
                plantJournalAddRequestDto.getLastWateringDay(),
                java.time.LocalDate.now()).getDays();                        // 마지막 물 준날과 지금 날짜를 비교하여 WaterDday계산
        if(WaterDDay > plantJournalAddRequestDto.getWaterCycle()){           // 위의 결과 값이 지정해준 WaterCycle보다 크다면 WaterCycle을 Dday로 지정
            WaterDDay = plantJournalAddRequestDto.getWaterCycle();
        }
        int nutritionDDay = plantJournalAddRequestDto.getNutritionCycle();   // 영양 주기 Dday를 영양 Cycle로 지정
        int repottingDDay = plantJournalAddRequestDto.getRepottingCycle();   // 분갈이 주기 Dday를 분갈이 Cycle로 지정
        PlantJournal plantJournal = new PlantJournal(plantJournalAddRequestDto,user,image,WaterDDay,nutritionDDay,repottingDDay);
        plantJournalRepository.save(plantJournal);
        return new PlantJournalAddResponseDto(plantJournal);
    }

    // 식물 일지 전체 조회
    public List<PlantJournalsRequestDto> PlantJournalsAll(User user) {
        List<PlantJournal> PlansJournalsList = plantJournalRepository.findAllByUser(user);
        List<PlantJournalsRequestDto> plantJournalsRequestDtoList = new ArrayList<>();
        for(PlantJournal plantJournal : PlansJournalsList){
            plantJournalsRequestDtoList.add(new PlantJournalsRequestDto(plantJournal));
        }
        return plantJournalsRequestDtoList;
    }

    // 내 식물 관리하기 식물 상세 조회
    public MyPlantManagementDTO MyPlantManageMent(User user, Long plantJournalId) {
        return new MyPlantManagementDTO(plantJournalRepository.findByUserAndId(user, plantJournalId));
    }

    // D-day 물 주기 버튼
    @Transactional
    public MsgResponseDto PlantWaterClick(User user, Long plantJournalId) {
        PlantJournal plantJournal = plantJournalRepository.findByUserAndId(user,plantJournalId);
        if(plantJournal.getWaterDDay() != 0){                                                                           // DDay가 아닐시, 예외처리
            throw new CustomException(ErrorCode.NO_WATER_D_DAY);
        }
        if(waterClickRepository.findByLocalDateAndUserAndPlantJournal(LocalDate.now(),user,plantJournal).isEmpty()){    // DB 안에 오늘 식물에 대해 클릭을 했는지 여부
            waterClickRepository.save(new WaterClick(user, plantJournal));                                              // DB에 오늘 클릭 정보 저장
            plantJournal.WaterClick(waterClickRepository.countAllByUserAndPlantJournal(user, plantJournal));            // 해당 식물 DB에 카운트 횟수 저장
            return new MsgResponseDto(SuccessCode.WATER_CLICK_OK);
        }else{
            throw new CustomException(ErrorCode.ALREADY_WATER_CILCK);
        }
    }

    // D-day 영양제 주기 버튼
    @Transactional
    public MsgResponseDto ClickPlantNutrition(User user, Long plantJournalId) {
        PlantJournal plantJournal = plantJournalRepository.findByUserAndId(user,plantJournalId);
        if(plantJournal.getNutritionDDay() != 0){
            throw new CustomException(ErrorCode.NO_NUTRITION_D_DAY);
        }
        if(nutritionClickRepository.findByLocalDateAndUserAndPlantJournal(LocalDate.now(), user, plantJournal).isEmpty()){
            nutritionClickRepository.save(new NutritionClick(user, plantJournal));
            plantJournal.NutritionClick(waterClickRepository.countAllByUserAndPlantJournal(user, plantJournal));
            return new MsgResponseDto(SuccessCode.NUTRITION_CLICK_OK);
        }else{
            throw new CustomException(ErrorCode.ALREADY_NUTRITION_CILCK);
        }
    }

    // D-day 분갈이 버튼
    @Transactional
    public   MsgResponseDto ClickPlantRepotting(User user, Long plantJournalId) {
        PlantJournal plantJournal = plantJournalRepository.findByUserAndId(user,plantJournalId);
        if(plantJournal.getRepottingDDay() != 0){
            throw new CustomException(ErrorCode.NO_REPOTTING_D_DAY);
        }
        if(repottingCilckRepository.findByLocalDateAndUserAndPlantJournal(LocalDate.now(), user, plantJournal).isEmpty()){
            repottingCilckRepository.save(new RepottingCilck(user, plantJournal));
            plantJournal.RepottingClick(waterClickRepository.countAllByUserAndPlantJournal(user, plantJournal));
            return new MsgResponseDto(SuccessCode.REPOTTING_CLICK_OK);
        }else{
            throw new CustomException(ErrorCode.ALREADY_REPOTTING_CILCK);
        }
    }

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
