package com.pulbatte.pulbatte.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginKakaoRequestDto {
    private String userId;
    private String nickname;

    public LoginKakaoRequestDto(String userId, String nickname){
        this.userId = userId;
        this.nickname = nickname;
    }
}