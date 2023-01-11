package com.pulbatte.pulbatte.plant.controller;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.plant.dto.*;
import com.pulbatte.pulbatte.plant.service.PlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/plant")
@RequiredArgsConstructor
public class PlantController {

    private final PlantService plantService;

    @PostMapping("/plantadd")  // 식물 추가
    public PlantJournalAddResponseDto PlantJournalAdd
            (@AuthenticationPrincipal UserDetailsImpl userDetails,
             @RequestPart PlantJournalAddRequestDto plantJournalAddRequestDto,
             @RequestPart(value = "image", required = false) MultipartFile multipartFile
            ) throws IOException {
        return plantService.PlantJournalAdd(userDetails.getUser(), plantJournalAddRequestDto, multipartFile);
    }

    @GetMapping("/plantjournals")  // 식물 일지 목록 불러오기
    public List<PlantJournalsRequestDto> PlantJournalsAll(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return plantService.PlantJournalsAll(userDetails.getUser());
    }

    @GetMapping("/plantjournal/{PlantJournalId}")  // 내 식물 관리 (식물 상세 보기)
    public MyPlantManagementDTO MyPlantManageMent(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long PlantJournalId
    ) {
        return plantService.MyPlantManageMent(userDetails.getUser(), PlantJournalId);
    }

    @PostMapping("/plantjournal/{PlantJournalId}/clickwater") // D-day 물 주기
    public MsgResponseDto ClickPlantWater(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long PlantJournalId
    ) {
        return  plantService.PlantWaterClick(userDetails.getUser(), PlantJournalId);
    }

    @PostMapping("/plantjournal/{PlantJournalId}/clicknutrition") // D-day 영양제 주기
    public MsgResponseDto ClickPlantNutrition(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long PlantJournalId
    ) {
        return  plantService.ClickPlantNutrition(userDetails.getUser(), PlantJournalId);
    }

    @PostMapping("/plantjournal/{PlantJournalId}/clickrepotting") // D-day 분갈이 하기
    public MsgResponseDto ClickPlantRepotting(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long PlantJournalId
    ) {
        return  plantService.ClickPlantRepotting(userDetails.getUser(), PlantJournalId);
    }
}
