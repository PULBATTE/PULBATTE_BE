package com.pulbatte.pulbatte.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.jwt.JwtUtil;
import com.pulbatte.pulbatte.post.dto.KakaoUserInfoDto;
import com.pulbatte.pulbatte.user.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class KakaoUserController {
    private final KakaoService kakaoService;

    @PostMapping("/kakao/callback")
    public KakaoUserInfoDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        /*String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);
        return new MsgResponseDto(SuccessCode.LOG_IN);*/
        return kakaoService.kakaoLogin(code, response);
    }
//}
//    @GetMapping("/kakao/callback")
//    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
//        // code: 카카오 서버로부터 받은 인가 코드
//        // 인가코드를 서비스로 전달
//        kakaoService.kakaoLogin(code, response);
//        return ResponseEntity.ok().body(new MsgResponseDto(SuccessCode.LOG_IN));
//    }
}
