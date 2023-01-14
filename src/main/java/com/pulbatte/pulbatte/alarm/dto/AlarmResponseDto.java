package com.pulbatte.pulbatte.alarm.dto;

import com.pulbatte.pulbatte.alarm.entity.AlarmType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmResponseDto {
    private String content;
    private AlarmType alarmType;

    @Builder
    public AlarmResponseDto(String content, AlarmType alarmType) {
        this.content = content;
        this.alarmType = alarmType;
    }
}
