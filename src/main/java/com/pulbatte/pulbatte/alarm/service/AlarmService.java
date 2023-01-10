package com.pulbatte.pulbatte.alarm.service;

import com.pulbatte.pulbatte.alarm.entity.Alarm;
import com.pulbatte.pulbatte.alarm.entity.AlarmType;
import com.pulbatte.pulbatte.alarm.repository.AlarmRepository;
import com.pulbatte.pulbatte.alarm.repository.EmitterRepository;
import com.pulbatte.pulbatte.plant.repository.PlantRepository;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlarmService {
    private final PlantRepository plantRepository;
    private final EmitterRepository emitterRepository;
    private final AlarmRepository alarmRepository;

    public SseEmitter reply(Long userId, String lastEventId) {
        Long timeout = 60 * 1000L * 60L;
        String emitterId = makeTimeIncludeId(userId);           // emitter 아이디 생성
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(timeout));            // emitter 생성
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));            // 시간이 만료되면 자동 삭제
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));

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
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
        }
    }

    private boolean hasLostData(String lastEventId) {
        return !lastEventId.isEmpty();
    }

    private void sendLostData(String lastEventId, Long userId, String emitterId, SseEmitter emitter) {
        Map<String, Object> eventCaches = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));
        eventCaches.entrySet().stream()
                .filter(e -> lastEventId.compareTo(e.getKey())<0)
                .forEach(e -> sendAlarm(emitter, e.getKey(), emitterId, e.getValue()));
    }

    public void send(AlarmType alarmType, String content, User user) {
        Alarm alarm = alarmRepository.save(createAlarm(alarmType, content, user));

        String receiveId = String.valueOf(user.getUserId());
        String eventId = receiveId + "_" + System.currentTimeMillis();
        Map<String, SseEmitter> emitters = emitterRepository.findAllEmitterStartWithByUserId(receiveId);
        emitters.forEach(
                (key, emitter) -> {
                    emitterRepository.saveEventCache(key, alarm);
                    sendAlarm(emitter, eventId, key, alarm);
                }
        );
    }

    private Alarm createAlarm(AlarmType alarmType, String content, User user) {
        return Alarm.builder()
                .alarmType(alarmType)
                .content(content)
                .user(user)
                .build();
    }
}
