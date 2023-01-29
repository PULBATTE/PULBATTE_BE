package com.pulbatte.pulbatte.plantTest.controller;

import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.plantTest.dto.PlantTestResponseDto;
import com.pulbatte.pulbatte.plantTest.service.PlantTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/plantTest")
@RequiredArgsConstructor
public class PlantTestController {
    private final PlantTestService plantTestService;

    @PostMapping("/{result}")
    public MsgResponseDto postPlantTest(
            @PathVariable("result") String result,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        plantTestService.postPlantTest(result,userDetails.getUser());
        return new MsgResponseDto(SuccessCode.COMPLETE_TEST);
    }
    @GetMapping
    public PlantTestResponseDto PlantTestResponseDto(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return plantTestService.getPlantTest(userDetails.getUser());
    }
}
