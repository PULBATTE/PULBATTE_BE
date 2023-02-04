package com.pulbatte.pulbatte.alarm.dto;

import com.pulbatte.pulbatte.alarm.entity.AlarmType;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmRequestDto {
    private AlarmType type;
    private String content;
    private String url;
    private User user;

    @Builder
    public AlarmRequestDto(AlarmType type, String content, String url, User user) {
        this.type = type;
        this.content = content;
        this.url = url;
        this.user = user;
    }
}
