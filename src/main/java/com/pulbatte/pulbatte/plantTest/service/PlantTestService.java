package com.pulbatte.pulbatte.plantTest.service;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.bignner.repository.BeginnerRepository;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.plantTest.dto.PlantTestResponseDto;
import com.pulbatte.pulbatte.plantTest.entity.PlantTest;
import com.pulbatte.pulbatte.plantTest.repository.PlantTestRepository;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlantTestService {

    private final BeginnerRepository beginnerRepository;
    private final PlantTestRepository plantTestRepository;
    private final UserRepository userRepository;

    @Transactional
    public PlantTestResponseDto getPlantTest(String result, User user){
        long resultCode = 0;
        switch (result) {
            case "1", "2", "3" -> resultCode = 1;
            case "4", "5", "6" -> resultCode = 2;
            case "7", "8", "9" -> resultCode = 3;
            case "10", "11", "12" -> resultCode = 4;
            case "13", "14", "15" -> resultCode = 5;
            case "16", "17", "18" -> resultCode = 6;
        }
        Beginner beginner = beginnerRepository.findById(resultCode).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BEGINNER_PLANT)
        );          // resultCode 와 일치하는 초보자용 식물
        PlantTest plantTest = plantTestRepository.findById(resultCode).orElseThrow(
                () -> new CustomException(ErrorCode.NO_TEST_RESULT)
        );       // resultCode 와 일치하는 식집사 테스트 결과
        User userTestResult =  userRepository.findByUserId(user.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_EXIST_USER)
        ); // token 에 있는 유저의 아이디와 일치하는 유저
        userTestResult.updateTestResult(beginner.getBeginnerPlantName());                   // 유저 테이블에 테스트 결과 업데이트
        return new PlantTestResponseDto(beginner,plantTest);
    }
}
