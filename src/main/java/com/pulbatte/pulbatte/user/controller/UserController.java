package com.pulbatte.pulbatte.user.controller;

import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.dto.RequestToken;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.jwt.TokenDto;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.user.dto.UserRequestDto;
import com.pulbatte.pulbatte.user.dto.SignupRequestDto;
import com.pulbatte.pulbatte.user.dto.UserResponseDto;
import com.pulbatte.pulbatte.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    // 회원 가입
    @PostMapping("/signup")
    public MsgResponseDto signup(
            @RequestBody
            @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return new MsgResponseDto(SuccessCode.SIGN_UP);
    }
    // 로그인
    @PostMapping("/signin")
    public TokenDto login(
            @RequestBody UserRequestDto loginRequestDto,
            HttpServletResponse response) {
        //클라이언트에 반환하기 위해 response 객체
        return userService.login(loginRequestDto, response);
    }
    // 아이디 중복 확인
    @GetMapping("/idDupleCheck")
    public MsgResponseDto checkUserNameDuplicate (
            @RequestParam("userId") String userId){
        return userService.checkUserIdDuplicate(userId);
    }
    // 유저 정보
    @GetMapping("/info")
    public UserResponseDto postUserInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.postUserInfo(userDetails.getUser());
    }
}
