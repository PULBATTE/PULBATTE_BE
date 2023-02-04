package com.pulbatte.pulbatte.alarm.controller;

import com.pulbatte.pulbatte.alarm.service.SseService;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class SseController {
    private final SseService sseService;

    /**
     consumes -> 들어오는 데이터 타입을 정의
     produces -> 반환하는 데이터 타입을 정의
     */
    @GetMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE, produces = "text/event-stream")
    public SseEmitter subscribe(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestHeader(value = "Last-Event-Id", required = false, defaultValue = "") String lastEventId,
            HttpServletResponse response
    ) throws IOException {
        response.addHeader("Content-Type","text/event-stream;charset=utf-8");
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Connection", "keep-alive");
        response.addHeader("X-Accel-Buffering","no");
        return sseService.subscribe(userDetails.getUser().getId(), lastEventId);
    }
}
