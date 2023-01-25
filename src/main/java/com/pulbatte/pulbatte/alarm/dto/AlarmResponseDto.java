package com.pulbatte.pulbatte.alarm.dto;

import com.pulbatte.pulbatte.alarm.entity.Alarm;
import com.pulbatte.pulbatte.alarm.entity.AlarmType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmResponseDto {
    private Long id;
    private String content;
    private AlarmType alarmType;
    private Boolean isRead;

    @QueryProjection            // Q파일 생성
    public AlarmResponseDto(Alarm alarm) {
        this.id = alarm.getId();
        this.content = alarm.getContent();
        this.alarmType = alarm.getAlarmType();
        this.isRead = alarm.getIsRead();
    }
}
