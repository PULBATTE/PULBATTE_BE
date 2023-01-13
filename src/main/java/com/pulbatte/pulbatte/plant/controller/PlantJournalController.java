package com.pulbatte.pulbatte.plant.controller;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.plant.dto.*;
import com.pulbatte.pulbatte.plant.service.PlantJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/plant")
@RequiredArgsConstructor
public class PlantJournalController {

    private final PlantJournalService plantJournalService;

    @PostMapping("/plantadd")  // 식물 추가
    public MsgResponseDto PlantJournalAdd
            (@AuthenticationPrincipal UserDetailsImpl userDetails,
             @RequestPart PlantJournalAddRequestDto plantJournalAddRequestDto,
             @RequestPart(value = "image", required = false) MultipartFile multipartFile
            ) throws IOException {
        plantJournalService.PlantJournalAdd(userDetails.getUser(), plantJournalAddRequestDto, multipartFile);
        return new MsgResponseDto(SuccessCode.CREATE_PLANT_JOURNAL);
    }

    @GetMapping("/plantjournals")  // 식물 일지 목록 불러오기
    public List<PlantJournalsRequestDto> PlantJournalsAll(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return plantJournalService.PlantJournalsAll(userDetails.getUser());
    }

    @GetMapping("/plantjournal/{plantjournalid}")  // 내 식물 관리 (식물 상세 보기)
    public MyPlantManagementDTO MyPlantManageMent(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid
    ) {
        return plantJournalService.MyPlantManageMent(userDetails.getUser(), plantjournalid);
    }

    @PostMapping("/plantjournal/{plantjournalid}/clickwater") // D-day 물 주기
    public MsgResponseDto ClickPlantWater(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid
    ) {
        return  plantJournalService.PlantWaterClick(userDetails.getUser(), plantjournalid);
    }

    @PostMapping("/plantjournal/{plantjournalid}/clicknutrition") // D-day 영양제 주기
    public MsgResponseDto ClickPlantNutrition(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid
    ) {
        return  plantJournalService.ClickPlantNutrition(userDetails.getUser(), plantjournalid);
    }

    @PostMapping("/plantjournal/{plantjournalid}/clickrepotting") // D-day 분갈이 하기
    public MsgResponseDto ClickPlantRepotting(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid
    ) {
        return  plantJournalService.ClickPlantRepotting(userDetails.getUser(), plantjournalid);
    }
}
