package com.pulbatte.pulbatte.user.service;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.jwt.JwtUtil;
import com.pulbatte.pulbatte.user.dto.SignInRequestDto;
import com.pulbatte.pulbatte.user.dto.SignupRequestDto;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.entity.UserRoleEnum;
import com.pulbatte.pulbatte.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  UserService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;


    private static final String ADMIN_TOKEN = "HangHae99NoHangHae130Yes";

    private static int signUpType = 0;

    // 회원가입
    @Transactional
    public MsgResponseDto signup(SignupRequestDto signupRequestDto) {


        String nickname = RandomStringUtils.random(6, true, true);                         // 닉네임 랜덤 생성


        String userId = signupRequestDto.getUserId();

        String password = passwordEncoder.encode(signupRequestDto.getPassword());

        // 중복 닉네임
        Optional<User> found = userRepository.findByUserId(userId);
        if (found.isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USERNAME);
        }
        UserRoleEnum role = UserRoleEnum.USER;

        // 권한 토큰키 검증
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new CustomException(ErrorCode.DISMATCH_ADMIN_TOKEN);
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(userId, password, nickname, signUpType, role);
        userRepository.save(user);
        return new MsgResponseDto(SuccessCode.SIGN_UP);
    }

    // 로그인
    @Transactional(readOnly = true)
    public MsgResponseDto login(SignInRequestDto loginRequestDto, HttpServletResponse response) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUserId(userId).orElseThrow(
                () -> new CustomException(ErrorCode.NO_EXIST_USER));

        // signUpType 확인
        if(user.getSignUpType() != signUpType){
            throw new CustomException(ErrorCode.NO_LOCAL_USER);
        }

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new CustomException(ErrorCode.DISMATCH_PASSWORD);
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserId(), user.getRole()));
        //add header로 헤더에 값 넣어주기 (키, 토큰)
        return new MsgResponseDto(SuccessCode.LOG_IN);
    }

    // 중복 아이디 체크
    public boolean checkUserIdDuplicate(String userId){
        boolean duplicateId = userRepository.existsByUserId(userId);
        if ( duplicateId == true ) {   // Username이 중복되는 경우 true, 중복되지 않은 경우 False
            return false;
        } else {
            return true;
        }
    }
}
