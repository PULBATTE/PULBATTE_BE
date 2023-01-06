package com.pulbatte.pulbatte.plantTest.controller;

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

    @GetMapping
    public PlantTestResponseDto getPlantTest(
            @RequestParam("result") String result,
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return plantTestService.getPlantTest(result,userDetails.getUser());
    }
}
