package com.pulbatte.pulbatte.plant.controller;

import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.plant.dto.PlantJournalDiaryRequestDto;
import com.pulbatte.pulbatte.plant.dto.PlantJournalDiaryResponseDto;
import com.pulbatte.pulbatte.plant.service.PlantJournalDiaryService;
import com.pulbatte.pulbatte.plant.service.PlantJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plant")
@RequiredArgsConstructor
public class PlantJournalDiaryController {

    private final PlantJournalService plantJournalService;
    private final PlantJournalDiaryService plantJournalDiaryService;

    @PostMapping("/plantjournal/diary/{plantjournalid}") // 식물 일지 다이어리 작성
    public PlantJournalDiaryResponseDto CreatePlantJournalDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PlantJournalDiaryRequestDto plantJournalDiaryRequestDto,
            @PathVariable Long plantjournalid
    ){
        return plantJournalDiaryService.CreatePlantJournalDiary(userDetails.getUser(),plantJournalDiaryRequestDto,plantjournalid);
    }

    @GetMapping("/plantjournal/diary/{plantjournalid}/{plantjournaldiaryid}") // 식물 일지 다이어리 상세보기
    public PlantJournalDiaryResponseDto GetPlantJournalDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid,
            @PathVariable Long plantjournaldiaryid
    ){
        return plantJournalDiaryService.GetPlantJournalDiary(userDetails.getUser(),plantjournalid,plantjournaldiaryid);
    }

    @GetMapping("/plantjournal/diarys/{plantjournalid}")  // 식물 일지 다이어리 리스트 조회
    public List<PlantJournalDiaryResponseDto> GetPlantJournalDiaryList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid
    ){
        return plantJournalDiaryService.GetPlantJournalDiaryList(userDetails.getUser(),plantjournalid);
    }
}
