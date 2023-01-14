package com.pulbatte.pulbatte.plantJournal.controller;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalAddRequestDto;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalsRequestDto;
import com.pulbatte.pulbatte.plantJournal.service.PlantJournalService;
import com.pulbatte.pulbatte.plantJournal.dto.MyPlantManagementDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlantJournalController {

    private final PlantJournalService plantJournalService;

    @PostMapping("/plantjournal")  // 식물 추가
    public MsgResponseDto CreatePlantJournal
            (@AuthenticationPrincipal UserDetailsImpl userDetails,
             @RequestPart PlantJournalAddRequestDto plantJournalAddRequestDto,
             @RequestPart(value = "image", required = false) MultipartFile multipartFile
            ) throws IOException {
        plantJournalService.CreatePlantJournal(userDetails.getUser(), plantJournalAddRequestDto, multipartFile);
        return new MsgResponseDto(SuccessCode.CREATE_PLANT_JOURNAL);
    }

    @GetMapping("/plantjournals")  // 식물 일지 목록 불러오기
    public List<PlantJournalsRequestDto> GetPlantJournalList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return plantJournalService.GetPlantJournalList(userDetails.getUser());
    }

    @GetMapping("/plantjournal/{plantjournalid}")  // 내 식물 관리 (식물 상세 보기)
    public MyPlantManagementDTO GetPlantJournal(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid
    ) {
        return plantJournalService.GetPlantJournal(userDetails.getUser(), plantjournalid);
    }

    @PostMapping("/plantjorunal/{plantjournalid}/click/{clicktag}") // D-Day 클릭
    public MsgResponseDto ClickDday(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid,
            @PathVariable String clicktag
    ){
        return plantJournalService.ClickDday(userDetails.getUser(),plantjournalid,clicktag);
    }
}
