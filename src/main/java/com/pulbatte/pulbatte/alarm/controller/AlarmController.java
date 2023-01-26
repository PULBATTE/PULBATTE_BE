package com.pulbatte.pulbatte.alarm.controller;

import com.pulbatte.pulbatte.alarm.dto.AlarmListResponseDto;
import com.pulbatte.pulbatte.alarm.service.AlarmService;
import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    // 알림 목록
    @GetMapping(value = "/alarm")
    public ResponseEntity<AlarmListResponseDto> getAlarmList(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(alarmService.getAlarmList(userDetails.getUser()));
    }

    // 알림 읽음 상태 처리
    @DeleteMapping(value = "/alarm")
    public ResponseEntity<MsgResponseDto> changeAlarmState(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(alarmService.changeAlarmState(userDetails.getUser()));
    }
}
