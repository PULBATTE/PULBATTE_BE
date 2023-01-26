package com.pulbatte.pulbatte.alarm.service;

import com.pulbatte.pulbatte.alarm.dto.AlarmListResponseDto;
import com.pulbatte.pulbatte.alarm.dto.AlarmResponseDto;
import com.pulbatte.pulbatte.alarm.repository.AlarmQueryRepository;
import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
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

    public MsgResponseDto changeAlarmState(User user) {
        queryRepository.changeState(user.getId());
        return new MsgResponseDto(SuccessCode.READ_ALARM);
    }
}
