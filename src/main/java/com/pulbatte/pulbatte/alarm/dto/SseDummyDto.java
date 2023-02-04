package com.pulbatte.pulbatte.alarm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SseDummyDto {
    private String lastEventId;

    @Builder
    public SseDummyDto(String lastEventId) {
        this.lastEventId = lastEventId;
    }
}
