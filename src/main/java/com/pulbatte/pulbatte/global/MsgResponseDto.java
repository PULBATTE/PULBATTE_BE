package com.pulbatte.pulbatte.global;

import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
@NoArgsConstructor
public class MsgResponseDto {
    private int statusCode;
    private String msg;

    public MsgResponseDto(SuccessCode successCode) {
        this.msg = successCode.getMessage();
        this.statusCode = successCode.getHttpStatus().value();
    }

    public MsgResponseDto(CustomException customException) {
        this.statusCode = customException.getStatuscode();
        this.msg = customException.getMsg();
    }
    public MsgResponseDto(MethodArgumentNotValidException ex) {
        this.msg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        this.statusCode = HttpStatus.BAD_REQUEST.value();
    }
}
