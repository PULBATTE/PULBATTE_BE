package com.pulbatte.pulbatte.user.dto;

import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import lombok.Getter;

@Getter
public class KakaoResponseDto {
    private int status;
    private String msg;

    public KakaoResponseDto(SuccessCode successCode){
        this.status = successCode.getHttpStatus().value();
        this.msg = successCode.getMessage();

    }
}