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
    private String url;
    private AlarmType alarmType;
    private Boolean isRead;
    private LocalDateTime createdAt;

    @QueryProjection            // Q파일 생성
    public AlarmResponseDto(Alarm alarm) {
        this.id = alarm.getId();
        this.content = alarm.getContent();
        this.url = alarm.getUrl();
        this.alarmType = alarm.getAlarmType();
        this.isRead = alarm.getIsRead();
        this.createdAt = alarm.getCreatedAt();
    }
}
