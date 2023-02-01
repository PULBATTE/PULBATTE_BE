package com.pulbatte.pulbatte.alarm.service;

import com.pulbatte.pulbatte.alarm.dto.AlarmResponseDto;
import com.pulbatte.pulbatte.alarm.entity.Alarm;
import com.pulbatte.pulbatte.alarm.entity.AlarmType;
import com.pulbatte.pulbatte.alarm.repository.EmitterRepository;
import com.pulbatte.pulbatte.alarm.repository.EmitterRepositoryImpl;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();


    private static final Long TIMEOUT = 60L * 1000L * 60L;

    public SseEmitter subscribe(Long userId, String lastEventId) {
        String emitterId = makeTimeIncludeId(userId);           // emitter 아이디 생성
        SseEmitter emitter= emitterRepository.save(emitterId, new SseEmitter(TIMEOUT));            // emitter 생성

        String receiveId = String.valueOf(userId);
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiveId);
        log.info(emitters.toString());

        try {
            emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
            emitter.onTimeout(() -> {           // 시간이 만료되면 repository에서 자동 삭제
                emitterRepository.deleteById(emitterId);
            });
            emitter.onError((e) -> {            // 오류가 발생하면 repository에서 자동 삭제
                emitterRepository.deleteById(emitterId);
                log.info(e.getMessage());
            });

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
                    .name("sse")
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
    @Async
    public void send(AlarmType alarmType, String content, User user) {
        log.info("send 실행");
        Alarm alarm = createAlarm(alarmType, content, user);
        String receiveId = String.valueOf(user.getId());

        // 알림을 받을 사용자의 SseEmitter 가져오기
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiveId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    AlarmResponseDto alarmResponseDto = new AlarmResponseDto(alarm);
                    log.info(String.valueOf(alarmResponseDto));
                    sendToClient(emitter, key, alarmResponseDto);
                }
        );
    }

    public void sendList(AlarmType alarmType, String content, List userList) {
        List<Alarm> alarms = new ArrayList<>();
        Map<String, SseEmitter> sseEmitters;

        for(int i=0; i<userList.size(); i++) {
            int finalI = i;
            sseEmitters = new HashMap<>();
            alarms.add(createAlarm(alarmType, content, (User) userList.get(i)));

            sseEmitters.putAll(emitterRepository.findAllEmitterStartWithByUserId(userList.get(i).toString()));
            sseEmitters.forEach(
                    (key, emitter) -> {
                        emitterRepository.saveEventCache(key, alarms.get(finalI));
                        sendToClient(emitter, key, alarms.get(finalI));
                    }
            );
        }
    }

    // 알림 생성
    private Alarm createAlarm(AlarmType alarmType, String content, User user) {
        return Alarm.builder()
                .alarmType(alarmType)
                .content(content)
                .user(user)
                .isRead(false)
                .build();
    }

    // 알림 전송
    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data, MediaType.APPLICATION_JSON));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
        }
    }
}
