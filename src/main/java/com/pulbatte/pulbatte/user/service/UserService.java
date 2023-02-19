package com.pulbatte.pulbatte.user.service;

import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.entity.RefreshToken;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.jwt.JwtUtil;
import com.pulbatte.pulbatte.global.dto.TokenDto;
import com.pulbatte.pulbatte.global.repository.RefreshTokenRepository;
import com.pulbatte.pulbatte.user.dto.UserRequestDto;
import com.pulbatte.pulbatte.user.dto.SignupRequestDto;
import com.pulbatte.pulbatte.user.dto.UserResponseDto;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "HangHae99NoHangHae130Yes";
    private static int signUpType = 0;

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
        String profile = "https://d3usc6dqsfeh3v.cloudfront.net/plantTest/%EA%B8%B0%EB%B3%B8%EC%9D%B4%EB%AF%B8%EC%A7%80.png";
        User user = new User(userId, password, nickname,signUpType, role,profile);
        userRepository.save(user);
        return new MsgResponseDto(SuccessCode.SIGN_UP);
    }
    // 로그인
    @Transactional
    public TokenDto login(UserRequestDto loginRequestDto, HttpServletResponse response) {
        String userId = loginRequestDto.getUserId();
        String password = loginRequestDto.getPassword();
        User user = userRepository.findByUserId(userId).orElseThrow(                                                // 아이디 확인
                () -> new CustomException(ErrorCode.NO_EXIST_USER));
        if(user.getSignUpType() != signUpType){                                                                     // signUpType 확인
            throw new CustomException(ErrorCode.NO_LOCAL_USER);
        }
        if(!passwordEncoder.matches(password, user.getPassword())){                                                 // 복호화 한뒤 비밀번호 확인
            throw new CustomException(ErrorCode.DISMATCH_PASSWORD);
        }
        TokenDto tokenDto = jwtUtil.createAllToken(loginRequestDto.getUserId());
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountUserId(loginRequestDto.getUserId().toString());

        if(refreshToken.isPresent()){
            refreshToken.get().updateToken(tokenDto.getRefreshToken(),tokenDto.getAccessToken());
        }else {
            RefreshToken newToken =new RefreshToken(tokenDto.getRefreshToken(),tokenDto.getAccessToken(),loginRequestDto.getUserId());
            refreshTokenRepository.save(newToken);
        }
        setHeader(response,tokenDto);
        return tokenDto;
    }
    // 중복 아이디 체크
    public MsgResponseDto checkUserIdDuplicate(String userId){
        boolean duplicateId = userRepository.existsByUserId(userId);
        if (duplicateId) {                    // Username이 중복되는 경우 true, 중복되지 않은 경우 False
            return new MsgResponseDto(new CustomException(ErrorCode.ALREADY_EXIST_USERNAME));
        } else {
            return new MsgResponseDto(SuccessCode.CHECK_USER_ID);
        }
    }
    // 유저 정보
    public UserResponseDto postUserInfo (User user){
        User userInfo = userRepository.findById(user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_EXIST_USER)
        );
        return new UserResponseDto(userInfo);
    }
    private void setHeader(HttpServletResponse response, TokenDto tokenDto){
        response.addHeader(JwtUtil.ACCESS_TOKEN,tokenDto.getAccessToken());
    }
}
