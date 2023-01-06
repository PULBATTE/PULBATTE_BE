package com.pulbatte.pulbatte.bignner.controller;

import com.pulbatte.pulbatte.bignner.dto.BeginnerResponseDto;
import com.pulbatte.pulbatte.bignner.service.BeginnerService;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.plant.dto.PlantResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/beginner")
@RequiredArgsConstructor
public class BeginnerController {
    private final BeginnerService beginnerService;

    @GetMapping("/plant/{beginnerId}")
    public BeginnerResponseDto getBeginnerPlant(
            @PathVariable Long beginnerId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return beginnerService.getBeginnerPlant(beginnerId,userDetails.getUser());
    }


}
