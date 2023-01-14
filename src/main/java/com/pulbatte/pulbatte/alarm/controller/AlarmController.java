package com.pulbatte.pulbatte.alarm.controller;

import com.pulbatte.pulbatte.alarm.service.AlarmService;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping(value = "/alarm", produces = "text/event-stream")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter connect(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestHeader(value = "Last-Event-Id", required = false, defaultValue = "") String lastEventId
    ) {
        return alarmService.subscribe(userDetails.getUser().getId(), lastEventId);
    }
}
