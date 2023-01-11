package com.pulbatte.pulbatte.plantTest.service;

import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.bignner.entity.BeginnerPlants;
import com.pulbatte.pulbatte.bignner.repository.BeginnerRepository;
import com.pulbatte.pulbatte.plantTest.dto.PlantTestResponseDto;
import com.pulbatte.pulbatte.plantTest.entity.PlantTest;
import com.pulbatte.pulbatte.plantTest.repository.PlantTestRepository;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
        Beginner beginner = beginnerRepository.findById(resultCode).orElseThrow();
        PlantTest plantTest = plantTestRepository.findById(resultCode).orElseThrow();
        User userTestResult =  userRepository.findByUserId(user.getUserId()).orElseThrow();
        userTestResult.updateTestResult(beginner.getBeginnerPlantName());
        return new PlantTestResponseDto(beginner,plantTest);
    }
}
