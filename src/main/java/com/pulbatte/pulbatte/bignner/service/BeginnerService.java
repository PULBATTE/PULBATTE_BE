package com.pulbatte.pulbatte.bignner.service;

import com.pulbatte.pulbatte.bignner.dto.BeginnerGraphResponseDto;
import com.pulbatte.pulbatte.bignner.dto.BeginnerRequestDto;
import com.pulbatte.pulbatte.bignner.dto.BeginnerResponseDto;
import com.pulbatte.pulbatte.bignner.entity.Beginner;
import com.pulbatte.pulbatte.bignner.entity.BeginnerGraph;
import com.pulbatte.pulbatte.bignner.entity.BeginnerUser;
import com.pulbatte.pulbatte.bignner.repository.BeginnerGraphRepository;
import com.pulbatte.pulbatte.bignner.repository.BeginnerRepository;
import com.pulbatte.pulbatte.bignner.repository.BeginnerUserRepository;
import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BeginnerService {

    private final BeginnerRepository beginnerRepository;
    private final BeginnerGraphRepository beginnerGraphRepository;
    private final BeginnerUserRepository beginnerUserRepository;

    // 가이드 식물 선택
    public List<BeginnerResponseDto> getBeginnerSelect(User user){
        List<Beginner> beginnerList = beginnerRepository.findAll();
        List<BeginnerResponseDto> beginnerResponseDtoList = new ArrayList<>();
        boolean overlap;
        boolean like ;
        for (Beginner beginner : beginnerList){
            if(beginner.getId()>6) break;
            if(user.getTestResult().equals(beginner.getBeginnerPlantName())){
                like = true;
            }else {
                like = false;
            }
            if(beginnerUserRepository.findByUserIdAndBeginnerBeginnerPlantName(user.getId(),beginner.getBeginnerPlantName()).isPresent()){
                overlap = true;
            }else {
                overlap = false;
            }
            beginnerResponseDtoList.add(new BeginnerResponseDto(beginner,like,overlap));
        }
        return beginnerResponseDtoList;
    }
    // 가이드 식물 등록
    public MsgResponseDto postBeginnerPlant(String beginnerName, User user){
        Beginner beginner = beginnerRepository.findByBeginnerPlantName(beginnerName).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BEGINNER_PLANT)
        );
        if(beginnerUserRepository.findByUserId(user.getId()).isEmpty()){
            LocalDate localDate = LocalDate.now();
            beginnerUserRepository.save(new BeginnerUser(beginner,localDate,user));
        }else {
            return new MsgResponseDto(new CustomException(ErrorCode.ALREADY_EXIST_DATE));
        }
        return new MsgResponseDto(SuccessCode.CREATE_BEGINNER_Plant);
    }
    // 가이드 식물 삭제
    public MsgResponseDto deleteBeginnerPlant(User user){
        BeginnerUser beginnerUser = beginnerUserRepository.findByUserId(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BEGINNER_PLANT)
        );
        beginnerUserRepository.delete(beginnerUser);
        return new MsgResponseDto(SuccessCode.DELETE_BEGINNER_Plant);
    }

    // 가이드 그래프 등록
    public MsgResponseDto postBeginnerGraph(BeginnerRequestDto beginnerRequestDto,User user){
        BeginnerUser beginnerUser = beginnerUserRepository.findByUserId(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_EXIST_USER)
        );
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(beginnerRequestDto.getLocalDate(),formatter);
        if(beginnerUser.getStartDate().plusDays(30).isAfter(localDate)){
            if(beginnerGraphRepository.findByLocalDateAndUserId(localDate, user.getId()).isEmpty()){ // 중복 확인
                beginnerGraphRepository.save(new BeginnerGraph(beginnerRequestDto,beginnerUser,user,localDate));
                return new MsgResponseDto(SuccessCode.CREATE_BEGINNER_GRAPH);
            }else {
                return new MsgResponseDto(new CustomException(ErrorCode.ALREADY_EXIST_DATE));
            }
        }else {
            return new MsgResponseDto(new CustomException(ErrorCode.END_GUIDE));
        }
    }
    // 가이드 식물 출력
    public BeginnerResponseDto getBeginnerPlant(User user) {
        BeginnerUser beginnerUser = beginnerUserRepository.findByUserId(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_EXIST_USER)
        );
        Beginner beginner = beginnerRepository.findById(beginnerUser.getBeginner().getId()).orElseThrow(
                ()-> new CustomException(ErrorCode.NO_BEGINNER_PLANT)
        );
        List<BeginnerGraphResponseDto> beginnerGraphResponseDtoList = new ArrayList<>();
        for (BeginnerGraph beginnerGraphValue : beginnerUser.getBeginnerGraphs()){
            beginnerGraphResponseDtoList.add(new BeginnerGraphResponseDto(beginnerGraphValue));
        }
        return new BeginnerResponseDto(beginner, beginnerGraphResponseDtoList);
    }

}