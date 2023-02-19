package com.pulbatte.pulbatte.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestToken {
    private String refreshToken;
    private String userEmail;
    private String accessToken;
}
