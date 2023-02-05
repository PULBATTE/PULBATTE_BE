package com.pulbatte.pulbatte.alarm.dto;

import com.pulbatte.pulbatte.alarm.entity.Alarm;
import com.pulbatte.pulbatte.alarm.entity.AlarmType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AlarmResponseDto {
    private Long id;
    private String content;
    private AlarmType alarmType;
    private Boolean isRead;
    private LocalDateTime createdAt;

    @Builder
    @QueryProjection            // Q파일 생성
    public AlarmResponseDto(Long id, String content, AlarmType type, Boolean isRead) {
        this.id = id;
        this.content = content;
        this.alarmType = type;
        this.isRead = isRead;
        this.createdAt = LocalDateTime.now();
    }
}
