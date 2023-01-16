package com.pulbatte.pulbatte.user.controller;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.user.dto.UserRequestDto;
import com.pulbatte.pulbatte.user.dto.SignupRequestDto;
import com.pulbatte.pulbatte.user.dto.UserResponseDto;
import com.pulbatte.pulbatte.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public MsgResponseDto login(
            @RequestBody UserRequestDto loginRequestDto,
            HttpServletResponse response) {
        //클라이언트에 반환하기 위해 response 객체
        userService.login(loginRequestDto, response);
        return new MsgResponseDto(SuccessCode.LOG_IN);
    }
    // 토큰 재발행
    @GetMapping("/issue/token")
    public MsgResponseDto reFreshToken(@AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletResponse response){
        return userService.reFreshToken(userDetails.getUser().getUserId(), response);
    }
    // 아이디 중복 확인
    @GetMapping("/idDupleCheck")
    public ResponseEntity<Boolean> checkUserNameDuplicate (
            @RequestParam String userId){
        return ResponseEntity.ok(userService.checkUserIdDuplicate(userId));
    }
    // 유저 정보
    @GetMapping("/info")
    public UserResponseDto postUserInfo(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.postUserInfo(userDetails.getUser());
    }
}
