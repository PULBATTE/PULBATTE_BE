package com.pulbatte.pulbatte.bignner.controller;

import com.pulbatte.pulbatte.bignner.dto.BeginnerResponseDto;
import com.pulbatte.pulbatte.bignner.service.BeginnerService;
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

    //식집사 가이드 페이지 - 식물 키우기 페이지
    @GetMapping("/{beginnerName}")
    public BeginnerResponseDto getBeginnerPlant(
            @PathVariable String beginnerName,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return beginnerService.getBeginnerPlant(beginnerName,userDetails.getUser());
    }
}
