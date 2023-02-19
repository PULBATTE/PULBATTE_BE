package com.pulbatte.pulbatte.global.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Entity
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String refreshToken;
    @NotBlank
    private String accessToken;
    @NotBlank
    private String accountUserId;

    public RefreshToken(String refreshToken, String accessToken,String userId) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.accountUserId = userId;
    }

    public RefreshToken updateToken(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        return this;
    }
}