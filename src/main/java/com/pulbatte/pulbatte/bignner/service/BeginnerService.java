package com.pulbatte.pulbatte.bignner.service;

import com.pulbatte.pulbatte.bignner.dto.BeginnerGraphResponseDto;
import com.pulbatte.pulbatte.bignner.dto.BeginnerRequestDto;
import com.pulbatte.pulbatte.bignner.dto.BeginnerResponseDto;
import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.bignner.entity.BeginnerGraph;
import com.pulbatte.pulbatte.bignner.repository.BeginnerGraphRepository;
import com.pulbatte.pulbatte.bignner.repository.BeginnerRepository;
import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BeginnerService {

    private final BeginnerRepository beginnerRepository;
    private final BeginnerGraphRepository beginnerGraphRepository;

    public List<BeginnerResponseDto> getBeginnerSelect(User user){
        List<Beginner> beginnerList = beginnerRepository.findAll();
        List<BeginnerResponseDto> beginnerResponseDtoList = new ArrayList<>();

        for (Beginner beginner : beginnerList){
            beginnerResponseDtoList.add(new BeginnerResponseDto(beginner));
        }
        return beginnerResponseDtoList;
    }
    public MsgResponseDto postBeginnerGraph(BeginnerRequestDto beginnerRequestDto,User user){
        /*Map<LocalDate,Integer> graphValue = new HashMap<>();

        graphValue.put(beginnerRequestDto.getLocalDate(),beginnerRequestDto.getValue());*/
        beginnerGraphRepository.save(new BeginnerGraph(beginnerRequestDto,user));

        return new MsgResponseDto(SuccessCode.CREATE_COMMENT);
    }

    public BeginnerResponseDto getBeginnerPlant(String beginnerName, User user) {
        Beginner beginner = beginnerRepository.findByBeginnerPlantName(beginnerName).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BEGINNER_PLANT)
        );
        BeginnerGraph beginnerGraph = beginnerGraphRepository.findByUserId(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_EXIST_USER)
        );
        List<BeginnerGraphResponseDto> beginnerGraphResponseDtoList = new ArrayList<>();
        for (BeginnerGraph beginnerGraphValue :beginner.getBeginnerGraphs()){
            beginnerGraphResponseDtoList.add(new BeginnerGraphResponseDto(beginnerGraphValue));
        }



        return new BeginnerResponseDto(beginner,beginnerGraphResponseDtoList);
    }

}