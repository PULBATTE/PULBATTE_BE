package com.pulbatte.pulbatte.global.jwt;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private String statusCode;

    public TokenDto(String accessToken, String refreshToken,String status) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.statusCode=status;
    }
}