package com.pulbatte.pulbatte.alarm.repository;

import com.pulbatte.pulbatte.alarm.entity.Alarm;
import com.pulbatte.pulbatte.alarm.entity.AlarmType;
import com.pulbatte.pulbatte.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EmitterRepositoryImplTest {

    private EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private Long DEFAULT_TIMEOUT = 60l * 1000L * 60L;

    @Test
    @DisplayName("Emitter 저장")
    void save() throws Exception{
        Long userId = 1L;
        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        Assertions.assertDoesNotThrow(() -> emitterRepository.save(emitterId, sseEmitter));
    }

    @Test
    @DisplayName("이벤트 저장")
    void saveEventCache() throws Exception{
        Long userId = 1L;
        String eventCacheId = userId + "_" + System.currentTimeMillis();
        Alarm alarm = new Alarm(AlarmType.comment, "내 게시글에 댓글이 등록되었습니다.", new User(1L));

        Assertions.assertDoesNotThrow(() -> emitterRepository.saveEventCache(eventCacheId, alarm));
    }

    @Test
    @DisplayName("사용자와 관련된 모든 Emitter 찾기")
    void findAllEmitterStartWithByUserId() throws Exception{
        Long userId = 1L;
        String emitterId1 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId3 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId3, new SseEmitter(DEFAULT_TIMEOUT));

        Map<String, SseEmitter> ActualResult = emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(userId));

        Assertions.assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("사용자와 관련된 모든 이벤트 찾기")
    void findAllEventCacheStartWithByUserId() throws Exception{
        Long userId = 1L;
        String eventCacheId1 = userId + "_" + System.currentTimeMillis();
        Alarm alarm1 = new Alarm(AlarmType.comment, "내 게시글에 댓글이 등록되었습니다.", new User(1L));
        emitterRepository.saveEventCache(eventCacheId1, alarm1);

        Thread.sleep(100);
        String eventCacheId2 = userId + "_" + System.currentTimeMillis();
        Alarm alarm2 = new Alarm(AlarmType.comment, "내 게시글에 댓글이 등록되었습니다.", new User(1L));
        emitterRepository.saveEventCache(eventCacheId2, alarm2);

        Thread.sleep(100);
        String eventCacheId3 = userId + "_" + System.currentTimeMillis();
        Alarm alarm3 = new Alarm(AlarmType.comment, "내 게시글에 댓글이 등록되었습니다.", new User(1L));
        emitterRepository.saveEventCache(eventCacheId3, alarm3);

        Map<String, Object> ActualResult = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));

        Assertions.assertEquals(3, ActualResult.size());
    }

    @Test
    @DisplayName("Emitter 삭제")
    void deleteById() throws Exception{
        Long userID = 1L;
        String emitterId = userID + "_" + System.currentTimeMillis();
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);

        emitterRepository.save(emitterId, sseEmitter);
        emitterRepository.deleteById(emitterId);

        Assertions.assertEquals(0, emitterRepository.findAllEmitterStartWithByUserId(emitterId).size());
    }

    @Test
    @DisplayName("사용자와 관련된 모든 Emitter 삭제")
    void deleteAllEmitterStartWithId() throws Exception{
        Long userId = 1L;
        String emitterId1 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId1, new SseEmitter(DEFAULT_TIMEOUT));

        Thread.sleep(100);
        String emitterId2 = userId + "_" + System.currentTimeMillis();
        emitterRepository.save(emitterId2, new SseEmitter(DEFAULT_TIMEOUT));

        emitterRepository.deleteAllEmitterStartWithId(String.valueOf(userId));

        Assertions.assertEquals(0, emitterRepository.findAllEmitterStartWithByUserId(String.valueOf(userId)).size());
    }

    @Test
    @DisplayName("사용자와 관련된 모든 이벤트 삭제")
    void deleteAllEventCacheStartWithId() throws Exception{
        Long userId = 1L;
        String eventCacheId1 = userId + "_" + System.currentTimeMillis();
        Alarm alarm1 = new Alarm(AlarmType.comment, "내 게시글에 댓글이 등록되었습니다.", new User(1L));
        emitterRepository.saveEventCache(eventCacheId1, alarm1);

        Thread.sleep(100);
        String eventCacheId2 = userId + "_" + System.currentTimeMillis();
        Alarm alarm2 = new Alarm(AlarmType.comment, "내 게시글에 댓글이 등록되었습니다.", new User(1L));
        emitterRepository.saveEventCache(eventCacheId2, alarm2);

        emitterRepository.deleteAllEventCacheStartWithId(String.valueOf(userId));

        Assertions.assertEquals(0, emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId)).size());
    }
}