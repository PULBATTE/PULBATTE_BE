package com.pulbatte.pulbatte.global.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private String statusCode;
    private LocalDateTime tokenTime;

    public TokenDto(String accessToken, String refreshToken,String status,LocalDateTime tokenTime){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.statusCode=status;
        this.tokenTime=tokenTime.plusSeconds(5);
    }
}