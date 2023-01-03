package com.pulbatte.pulbatte.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.jwt.JwtUtil;
import com.pulbatte.pulbatte.user.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class KakaoUserController {

    private final KakaoService kakaoService;

    @PostMapping("/loginKakao") // code: 카카오 서버로부터 받은 인가 코드
    public MsgResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        /*String createToken = */kakaoService.kakaoLogin(code, response);
        // Cookie 생성 및 직접 브라우저에 Set
      /*  Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);*/

        return new MsgResponseDto(SuccessCode.LOG_IN);
    }
}
