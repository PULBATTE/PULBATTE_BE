package com.pulbatte.pulbatte.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class AlarmListResponseDto {
    private List<AlarmResponseDto> alarmList = new ArrayList<>();

    @Builder
    public AlarmListResponseDto(List<AlarmResponseDto> alarms) {
        this.alarmList =alarms;
    }
}
