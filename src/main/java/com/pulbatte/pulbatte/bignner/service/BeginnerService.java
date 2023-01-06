package com.pulbatte.pulbatte.bignner.service;

import com.pulbatte.pulbatte.bignner.dto.BeginnerResponseDto;
import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.bignner.repository.BeginnerRepository;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.plant.dto.PlantResponseDto;
import com.pulbatte.pulbatte.plant.entity.Plant;
import com.pulbatte.pulbatte.post.entity.Post;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeginnerService {

    private final BeginnerRepository beginnerRepository;

    public BeginnerResponseDto getBeginnerPlant(Long beginnerId,User user) {
        Beginner beginner = beginnerRepository.findById(beginnerId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
        );

        return new BeginnerResponseDto(beginner);
    }

}
