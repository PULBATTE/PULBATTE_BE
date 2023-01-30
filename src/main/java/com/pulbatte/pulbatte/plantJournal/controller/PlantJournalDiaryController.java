package com.pulbatte.pulbatte.plantJournal.controller;

import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.plantJournal.dto.CalendarResponseDto;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalDiaryRequestDto;
import com.pulbatte.pulbatte.plantJournal.dto.PlantJournalDiaryResponseDto;
import com.pulbatte.pulbatte.plantJournal.service.PlantJournalDiaryService;
import com.pulbatte.pulbatte.plantJournal.service.PlantJournalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plantjournal")
@RequiredArgsConstructor
public class PlantJournalDiaryController {

    private final PlantJournalService plantJournalService;
    private final PlantJournalDiaryService plantJournalDiaryService;

    @PostMapping("/diary/{plantjournalid}") // 식물 일지 다이어리 작성
    public MsgResponseDto CreatePlantJournalDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody PlantJournalDiaryRequestDto plantJournalDiaryRequestDto,
            @PathVariable Long plantjournalid
    ){
        plantJournalDiaryService.CreatePlantJournalDiary(userDetails.getUser(),plantJournalDiaryRequestDto,plantjournalid);
        return new MsgResponseDto(SuccessCode.CREATE_PLANT_JOURNAL_DIARY);
    }

    @GetMapping("/diary/calendar/{plantjournalid}")
    public List<CalendarResponseDto> GetCalendar(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid
            ){
        return plantJournalDiaryService.GetCalendar(userDetails.getUser(),plantjournalid);
    }

    @GetMapping("/diary/{plantjournalid}/{plantjournaldiaryid}") // 식물 일지 다이어리 상세보기
    public PlantJournalDiaryResponseDto GetPlantJournalDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid,
            @PathVariable Long plantjournaldiaryid
    ){
        return plantJournalDiaryService.GetPlantJournalDiary(userDetails.getUser(),plantjournalid,plantjournaldiaryid);
    }

    @GetMapping("/diarys/{plantjournalid}")  // 식물 일지 다이어리 리스트 조회
    public List<PlantJournalDiaryResponseDto> GetPlantJournalDiaryList(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid
    ){
        return plantJournalDiaryService.GetPlantJournalDiaryList(userDetails.getUser(),plantjournalid);
    }

    @PutMapping("/diary/{plantjournalid}/{plantjournaldiaryid}") // 식물 일지 다이어리 수정
    public MsgResponseDto UpdatePlantJournalDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid,
            @PathVariable Long plantjournaldiaryid,
            @RequestBody PlantJournalDiaryRequestDto plantJournalDiaryRequestDto
    ){
        plantJournalDiaryService.UpdatePlantJournalDiary(userDetails.getUser(),plantjournalid,plantjournaldiaryid,plantJournalDiaryRequestDto);
        return new MsgResponseDto(SuccessCode.UPDATE_PLANT_JOURNAL_DIARY);
    }

    @DeleteMapping("/diary/{plantjournalid}/{plantjournaldiaryid}") // 식물 일지 다이어리 삭제
    public MsgResponseDto DeletePlantJournalDiary(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantjournalid,
            @PathVariable Long plantjournaldiaryid
    ){
        plantJournalDiaryService.DeletePlantJournalDiary(userDetails.getUser(),plantjournalid,plantjournaldiaryid);
        return new MsgResponseDto(SuccessCode.DELETE_PLANT_JOURNAL_DIARY);
    }
}
