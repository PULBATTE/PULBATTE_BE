package com.pulbatte.pulbatte.bignner.controller;

import com.pulbatte.pulbatte.bignner.dto.BeginnerRequestDto;
import com.pulbatte.pulbatte.bignner.dto.BeginnerResponseDto;
import com.pulbatte.pulbatte.bignner.service.BeginnerService;
import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/beginner/plant")
@RequiredArgsConstructor
public class BeginnerController {
    private final BeginnerService beginnerService;

    //식집사 가이드 페이지 - 식물 선택 페이지
    @GetMapping
    public List<BeginnerResponseDto> getBeginnerSelect(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return beginnerService.getBeginnerSelect(userDetails.getUser());
    }
    // 식집사 가이드 페이지 - 가이드 식물 등록
    @PostMapping("/{beginnerId}")
    public MsgResponseDto postBeginnerPlant(
            @PathVariable Long beginnerId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return beginnerService.postBeginnerPlant(beginnerId,userDetails.getUser());
    }
    // 식집사 갑이드 페이지 - 가이드 식물 삭제
    @DeleteMapping
    public MsgResponseDto deleteBeginnerPlant(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return beginnerService.deleteBeginnerPlant(userDetails.getUser());
    }

    // 식집사 가이드 페이지 - 식물 키우기 페이지 그래프 입력
    @PostMapping
    public MsgResponseDto postBeginnerGraph(
            @RequestBody BeginnerRequestDto beginnerRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return beginnerService.postBeginnerGraph(beginnerRequestDto,userDetails.getUser());
    }
    //식집사 가이드 페이지 - 식물 키우기 페이지
    @GetMapping("/my")
    public BeginnerResponseDto getBeginnerPlant(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return beginnerService.getBeginnerPlant(userDetails.getUser());
    }
}