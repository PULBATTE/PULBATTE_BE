package com.pulbatte.pulbatte.bignner.service;

import com.pulbatte.pulbatte.bignner.dto.BeginnerResponseDto;
import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.bignner.repository.BeginnerRepository;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeginnerService {

    private final BeginnerRepository beginnerRepository;

    public List<BeginnerResponseDto> getBeginnerSelect(User user){
        List<Beginner> beginnerList = beginnerRepository.findAll();
        List<BeginnerResponseDto> beginnerResponseDtoList = new ArrayList<>();

        for (Beginner beginner : beginnerList){
            beginnerResponseDtoList.add(new BeginnerResponseDto(beginner));
        }
        return beginnerResponseDtoList;
    }

    public BeginnerResponseDto getBeginnerPlant(String beginnerName, User user) {
        Beginner beginner = beginnerRepository.findByBeginnerPlantName(beginnerName).orElseThrow(
                () -> new CustomException(ErrorCode.NO_POST_FOUND)
        );

        return new BeginnerResponseDto(beginner);
    }

}