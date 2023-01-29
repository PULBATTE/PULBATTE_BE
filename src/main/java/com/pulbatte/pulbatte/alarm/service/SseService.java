package com.pulbatte.pulbatte.alarm.service;

import com.pulbatte.pulbatte.alarm.dto.AlarmResponseDto;
import com.pulbatte.pulbatte.alarm.entity.Alarm;
import com.pulbatte.pulbatte.alarm.entity.AlarmType;
import com.pulbatte.pulbatte.alarm.repository.AlarmRepository;
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
    private final AlarmRepository alarmRepository;

    private static final Long TIMEOUT = 60 * 1000L * 60L;

    public SseEmitter subscribe(Long userId, String lastEventId) {
        String emitterId = makeTimeIncludeId(userId);           // emitter 아이디 생성
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(TIMEOUT));            // emitter 생성

        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));            // 시간이 만료되면 자동 삭제
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        emitter.onError((e) -> emitterRepository.deleteById(emitterId));

        if(!emitterRepository.findAllEmitterStartWithByUserId(userId).isEmpty()) {
            SseEmitter sseEmitter = emitterRepository.findAllEmitterStartWithByUserId(userId).get(userId+1);
//            sseEmitter.complete();
            SseEmitter.SseEventBuilder eventBuilder = SseEmitter.event()
                    .reconnectTime(500)
                    .data(MediaType.APPLICATION_JSON);

            try {
                emitter.send(eventBuilder);
            } catch (IOException exception) {
                emitterRepository.deleteById(emitterId);
            }
            emitterRepository.deleteAllEmitterStartWithId(userId);
        }

        // 더미 데이터를 보내 503 에러 방지
        String eventId = makeTimeIncludeId(userId);
        sendAlarm(emitter, eventId, emitterId, "EventStream Created. [userId = " + userId + "]");

        if(hasLostData(lastEventId)) {
            sendLostData(lastEventId, userId, emitterId, emitter);
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
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(userId);
        eventCaches.entrySet().stream()
                .filter(e -> lastEventId.compareTo(e.getKey())<0)
                .forEach(e -> sendAlarm(emitter, e.getKey(), emitterId, e.getValue()));
    }

    @Async
    public void send(AlarmType alarmType, String content, User user) {
        Alarm alarm = alarmRepository.save(createAlarm(alarmType, content, user));
        Long receiveId = user.getId();

        String eventId = receiveId + "_" + System.currentTimeMillis();
        // SseEmitter 가져오기
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiveId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
//                    AlarmResponseDto alarmResponseDto = new AlarmResponseDto(alarm);
//                    sendAlarm(emitter, eventId, key, alarmResponseDto);
                    sendToClient(emitter, key, alarm);
                }
        );
    }

    public void sendList(List userList, String content, AlarmType alarmType) {
        List<Alarm> alarms = new ArrayList<>();
        Map<String, SseEmitter> sseEmitters;

        for(int i=0; i<userList.size(); i++) {
            int finalI = i;
            sseEmitters = new HashMap<>();
            alarms.add(createAlarm(alarmType, content, (User) userList.get(i)));

            sseEmitters.putAll(emitterRepository.findAllEmitterStartWithByUserId(((User) userList.get(i)).getId()));
            sseEmitters.forEach(
                    (key, emitter) -> {
                        emitterRepository.saveEventCache(key, alarms.get(finalI));
                        sendToClient(emitter, key, alarms.get(finalI));
                    }
            );
        }
    }

    private Alarm createAlarm(AlarmType alarmType, String content, User user) {
        return Alarm.builder()
                .alarmType(alarmType)
                .content(content)
                .user(user)
                .isRead(false)
                .build();
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data, MediaType.APPLICATION_JSON)
                    .reconnectTime(0));
            emitter.complete();
            emitterRepository.deleteById(id);
        } catch (Exception exception) {
            emitterRepository.deleteById(id);
            emitter.completeWithError(exception);
        }
    }
}
