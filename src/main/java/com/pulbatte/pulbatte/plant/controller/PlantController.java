package com.pulbatte.pulbatte.plant.controller;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.plant.dto.*;
import com.pulbatte.pulbatte.plant.entity.Plant;
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

    @GetMapping("/plantjournal/{plantjournalid}")  // 내 식물 관리 (식물 상세 보기)
    public MyPlantManagementDTO MyPlantManageMent(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long PlantJournalId
    ) {
        return plantService.MyPlantManageMent(userDetails.getUser(), PlantJournalId);
    }

    @PostMapping("/plantjournal/diary/{plantjournalid}") // 식물 일지 다이어리 작성
    public PlantJournalDiaryResponseDto CreatePlantJournalDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PlantJournalDiaryRequestDto plantJournalDiaryRequestDto,
            @PathVariable Long plantJournalId
    ){
        return plantService.CreatePlantJournalDiary(userDetails.getUser(),plantJournalDiaryRequestDto,plantJournalId);
    }

    @GetMapping("/plantjournal/diary/{plantjournalid}/{plantjournaldiaryid}") // 식물 일지 다이어리 상세보기
    public PlantJournalDiaryResponseDto GetPlantJournalDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantJournalId,
            @PathVariable Long plantjournaldiaryid
    ){
        return plantService.GetPlantJournalDiary(userDetails.getUser(),plantJournalId,plantjournaldiaryid);
    }

    @GetMapping("plantjournal/diarys")  // 식물 일지 다이어리 리스트 조회
    public List<PlantJournalDiaryResponseDto> GetPlantJournalDiaryList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantJournalId
    ){
        return plantService.GetPlantJournalDiaryList(userDetails.getUser(),plantJournalId);
    }

    @PostMapping("/plantjournal/{plantJournalId}/clickwater") // D-day 물 주기
    public MsgResponseDto ClickPlantWater(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long PlantJournalId
    ) {
        return  plantService.PlantWaterClick(userDetails.getUser(), PlantJournalId);
    }

    @PostMapping("/plantjournal/{plantJournalId}/clicknutrition") // D-day 영양제 주기
    public MsgResponseDto ClickPlantNutrition(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long PlantJournalId
    ) {
        return  plantService.ClickPlantNutrition(userDetails.getUser(), PlantJournalId);
    }

    @PostMapping("/plantjournal/{plantJournalId}/clickrepotting") // D-day 분갈이 하기
    public MsgResponseDto ClickPlantRepotting(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long PlantJournalId
    ) {
        return  plantService.ClickPlantRepotting(userDetails.getUser(), PlantJournalId);
    }
}
