package com.pulbatte.pulbatte.user.controller;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.user.dto.UserRequestDto;
import com.pulbatte.pulbatte.user.dto.SignupRequestDto;
import com.pulbatte.pulbatte.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

/*    private final UserKakaoService userKakaoService;*/

    @PostMapping("/signup")
    public MsgResponseDto signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);

        return new MsgResponseDto(SuccessCode.SIGN_UP);
    }


    @PostMapping("/signin")
    public MsgResponseDto login(@RequestBody UserRequestDto loginRequestDto, HttpServletResponse response) {
        //클라이언트에 반환하기 위해 response 객체
        userService.login(loginRequestDto, response);

        return new MsgResponseDto(SuccessCode.LOG_IN);
    }

/*    @PostMapping("/loginKakao")
    public MsgResponseDto kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        String createToken = userKakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);
        return new MsgResponseDto(SuccessCode.LOG_IN);
    }*/
    // 아이디 중복 확인
    @GetMapping("/idDupleCheck")
    public ResponseEntity<Boolean> checkUserNameDuplicate (@RequestParam String userId){
        return ResponseEntity.ok(userService.checkUserIdDuplicate(userId));
    }
}
