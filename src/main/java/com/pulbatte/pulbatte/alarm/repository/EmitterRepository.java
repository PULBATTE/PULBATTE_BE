package com.pulbatte.pulbatte.alarm.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    SseEmitter save(String emitterId, SseEmitter sseEmitter);           // Emitter 저장
    void saveEventCache(String eventCacheId, Object event);         // 이벤트 저장

    Map<String, SseEmitter> findAllEmitterStartWithByUserId(Long userId);         // 사용자와 관련된 모든 Emitter 찾기
    Map<String, Object> findAllEventCacheStartWithByUserId(Long userId);          // 사용자와 관련된 모든 이벤트 찾기

    void deleteById(String id);             // Emitter 삭제
    void deleteAllEmitterStartWithId(Long userId);            // 사용자와 관련된 모든 Emitter 삭제
    void deleteAllEventCacheStartWithId(Long userId);             // 사용자와 관련된 모든 이벤트 삭제
}
