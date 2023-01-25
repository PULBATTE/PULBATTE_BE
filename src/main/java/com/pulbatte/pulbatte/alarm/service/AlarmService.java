package com.pulbatte.pulbatte.alarm.service;

import com.pulbatte.pulbatte.alarm.dto.AlarmListResponseDto;
import com.pulbatte.pulbatte.alarm.dto.AlarmResponseDto;
import com.pulbatte.pulbatte.alarm.repository.AlarmQueryRepository;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmQueryRepository queryRepository;

    public AlarmListResponseDto getAlarmList(User user) {
        List<AlarmResponseDto> alarmList = queryRepository.findAllByUserId(user.getId());
        return AlarmListResponseDto.builder()
                .alarms(alarmList)
                .build();
    }
}
