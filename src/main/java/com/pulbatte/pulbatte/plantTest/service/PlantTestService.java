package com.pulbatte.pulbatte.plantTest.service;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.bignner.repository.BeginnerRepository;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.plantTest.dto.PlantTestResponseDto;
import com.pulbatte.pulbatte.plantTest.entity.TestResult;
import com.pulbatte.pulbatte.plantTest.repository.TestResultRepository;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlantTestService {

    private final BeginnerRepository beginnerRepository;
    private final TestResultRepository testResultRepository;
    private final UserRepository userRepository;

    @Transactional
    public void postPlantTest(String result, User user){
        User userTestResult =  userRepository.findByUserId(user.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_EXIST_USER)
        );      // token 에 있는 유저의 아이디와 일치하는 유저
        userTestResult.updateTestResult(result);                   // 유저 테이블에 테스트 결과 업데이트
    }

    @Transactional
    public PlantTestResponseDto getPlantTest(User user){
        boolean testResultBoolean= false;
        if(!user.getTestResult().isEmpty()){
            testResultBoolean = true;
        }
        String resultCode = user.getTestResult().substring(0,4);
        String plantResultCode = user.getTestResult().substring(4);
        Beginner beginner = beginnerRepository.findByResultPlantCode(plantResultCode).orElseThrow(
                () -> new CustomException(ErrorCode.NO_TEST_RESULT)
        );       // resultCode 와 일치하는 식집사 테스트 결과
        TestResult testResult = testResultRepository.findByResultCode(resultCode).orElseThrow(
                () -> new CustomException(ErrorCode.NO_TEST_RESULT)
        );

        return new PlantTestResponseDto(beginner,testResult,testResultBoolean);
    }
}