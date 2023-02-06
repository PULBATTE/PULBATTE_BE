package com.pulbatte.pulbatte.global.controller;

import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.dto.RequestToken;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.dto.TokenDto;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.global.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {
    private final TokenService tokenService;

    // 토큰 재발행
    @PostMapping("/retoken")
    public TokenDto reFreshToken(
            /*@AuthenticationPrincipal UserDetailsImpl userDetails,*/
            @RequestHeader("Authorization") String authorization,
            @RequestBody RequestToken requestToken,
            HttpServletResponse response){
        return tokenService.reFreshToken(/*userDetails.getUser(), */authorization,response , requestToken);
    }
    // 리프레시 토큰 삭제
    @DeleteMapping("/deletetoken")
    public MsgResponseDto deleteToken(
            @AuthenticationPrincipal UserDetailsImpl userDetails
            ){
        tokenService.deleteToken(userDetails.getUser());
        return new MsgResponseDto(SuccessCode.DELETE_REFRESH_TOKEN);
    }
}
