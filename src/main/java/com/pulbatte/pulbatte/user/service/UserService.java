package com.pulbatte.pulbatte.user.service;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.jwt.JwtUtil;
import com.pulbatte.pulbatte.user.dto.UserRequestDto;
import com.pulbatte.pulbatte.user.dto.SignupRequestDto;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.entity.UserRoleEnum;
import com.pulbatte.pulbatte.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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

    // 회원가입
    @Transactional
    public MsgResponseDto signup(SignupRequestDto signupRequestDto) {
        String nickname = RandomStringUtils.random(6, true, true);      // 닉네임 난수 처리                    // 닉네임 랜덤 생성
        String userId = signupRequestDto.getUserId();                                       // 입력받은 id(email 형식)
        String password = passwordEncoder.encode(signupRequestDto.getPassword());           // jwt를 이용한 비밀번호 암호화

        Optional<User> found = userRepository.findByUserId(userId);
        if (found.isPresent()) {                                                            // 중복 닉네임이 있는 경우
            throw new CustomException(ErrorCode.ALREADY_EXIST_USERNAME);                    // 에러 출력
        }
        UserRoleEnum role = UserRoleEnum.USER;

        if (signupRequestDto.isAdmin()) {                                                   // 유저 권한 확인
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {                    // admin으로 회원가입시 토큰키가 맞는지 확인
                throw new CustomException(ErrorCode.DISMATCH_ADMIN_TOKEN);                  // 다르면 에러 출력
            }
            role = UserRoleEnum.ADMIN;                                                      // 맞으면 admin으로 회원가입
        }

        User user = new User(userId, password, nickname, role);
        userRepository.save(user);
        return new MsgResponseDto(SuccessCode.SIGN_UP);
    }

    // 로그인
    @Transactional(readOnly = true)
    public MsgResponseDto login(UserRequestDto loginRequestDto, HttpServletResponse response) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUserId(userId).orElseThrow(                                                // 아이디 확인
                () -> new CustomException(ErrorCode.NO_EXIST_USER));

        if(!passwordEncoder.matches(password, user.getPassword())){                                                 // 복호화 한뒤 비밀번호 확인
            throw new CustomException(ErrorCode.DISMATCH_PASSWORD);
        }
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserId(), user.getRole()));    // 헤더에 토큰 발급
        return new MsgResponseDto(SuccessCode.LOG_IN);
    }

    // 중복 아이디 체크
    public boolean checkUserIdDuplicate(String userId){
        boolean duplicateId = userRepository.existsByUserId(userId);
        if ( duplicateId == true ) {                    // Username이 중복되는 경우 true, 중복되지 않은 경우 False
            return false;
        } else {
            return true;
        }
    }
}
