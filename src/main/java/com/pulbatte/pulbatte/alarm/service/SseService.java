package com.pulbatte.pulbatte.alarm.service;

import com.pulbatte.pulbatte.alarm.dto.AlarmRequestDto;
import com.pulbatte.pulbatte.alarm.dto.AlarmResponseDto;
import com.pulbatte.pulbatte.alarm.entity.Alarm;
import com.pulbatte.pulbatte.alarm.repository.AlarmRepository;
import com.pulbatte.pulbatte.alarm.repository.EmitterRepository;
import com.pulbatte.pulbatte.alarm.repository.EmitterRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final AlarmRepository alarmRepository;

    private static final Long TIMEOUT = 60L * 1000L * 60L;              // 유효시간

    public SseEmitter subscribe(Long userId, String lastEventId) throws IOException {
        log.info("Id of lastEventId: {}", lastEventId);

        String emitterId = makeTimeIncludeId(userId);           // emitter 아이디 생성
        SseEmitter emitter= emitterRepository.save(emitterId, new SseEmitter(TIMEOUT));            // emitter 생성
        log.info("Emitter created: {}", emitter);

        emitter.onCompletion(() -> {
            log.info("onCompletion callback");
            emitterRepository.deleteById(emitterId);
        });
        emitter.onTimeout(() -> {           // 시간이 만료되면 repository에서 자동 삭제
            log.info("onTimeout callback");
            emitter.complete();
            emitterRepository.deleteById(emitterId);
        });

        try {
            // 더미 데이터를 보내 503 에러 방지
            String eventId = makeTimeIncludeId(userId);
            sendAlarm(emitter, eventId, emitterId, "EventStream Created. [userId = " + userId + "]");

            // 받지 못한 데이터가 있을 경우 다시 전송
            if(hasLostData(lastEventId)) {
                sendLostData(lastEventId, userId, emitterId, emitter);
            }
        } catch (Exception exception) {
            emitterRepository.deleteById(emitterId);
        }
        return emitter;
    }

    // emitterId 생성
    private String makeTimeIncludeId(Long userId) {
        return userId + "_" + System.currentTimeMillis();
    }

    private void sendAlarm(SseEmitter emitter, String eventId, String emitterId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(eventId)
                    .data(data, MediaType.APPLICATION_JSON));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    // lastEventId가 존재하는지
    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));
        eventCaches.entrySet().stream()
                .filter(e -> lastEventId.compareTo(e.getKey())<0)
                .forEach(e -> sendAlarm(emitter, e.getKey(), emitterId, e.getValue()));
    }

    // 사용자에게 알림 전송
    @Async              // event를 비동기로 동작
    @Transactional(propagation = Propagation.REQUIRES_NEW)              // 메소드를 하나의 transaction으로 묶어둠
    public void send(AlarmRequestDto requestDto) {
        Alarm alarm = alarmRepository.save(createAlarm(requestDto));
        AlarmResponseDto alarmResponseDto  = AlarmResponseDto.builder()
                .id(alarm.getId())
                .content(alarm.getContent())
                .type(alarm.getAlarmType())
                .isRead(alarm.getIsRead())
                .build();
        String receiveId = String.valueOf(requestDto.getUser().getId());

        // 알림을 받을 사용자의 SseEmitter 가져오기
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiveId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarmResponseDto);
                    sendToClient(emitter, key, alarmResponseDto);
                    log.info("alarm = {}", alarmResponseDto.getContent());
                }
        );
    }

    // 알림 생성
    private Alarm createAlarm(AlarmRequestDto requestDto) {
        return Alarm.builder()
                .alarmType(requestDto.getType())
                .content(requestDto.getContent())
                .url(requestDto.getUrl())
                .user(requestDto.getUser())
                .isRead(false)
                .build();
    }

    // 알림 전송
    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("connect")
                    // 실제 전송할 데이터 문자열
                    .data(data, MediaType.APPLICATION_JSON));
        } catch (IOException exception) {
            emitter.completeWithError(exception);
            emitterRepository.deleteById(id);
        }
    }
}
