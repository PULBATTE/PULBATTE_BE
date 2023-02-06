package com.pulbatte.pulbatte.alarm.listener;

import com.pulbatte.pulbatte.alarm.dto.AlarmRequestDto;
import com.pulbatte.pulbatte.alarm.service.SseService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class SseListener {
    private final SseService sseService;

//    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)              // 기본값은 AFTER_COMMIT으로 트랜잭션이 commit되었을 때 event를 실행
    @Async
    public void handleAlarm(AlarmRequestDto requestDto) {
        sseService.send(requestDto);
    }
}
