package com.pulbatte.pulbatte.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.jwt.JwtUtil;
import com.pulbatte.pulbatte.user.dto.KakaoResponseDto;
import com.pulbatte.pulbatte.user.dto.LoginKakaoRequestDto;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.entity.UserRoleEnum;
import com.pulbatte.pulbatte.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final PasswordEncoder passwordEncoder;

    private final static int signUpType = 1;


    public KakaoResponseDto kakaoLogin(String code, HttpServletResponse response) throws JsonProcessingException {
//      1. "인가 코드"로 "액세스 토큰" 요청
        /*String accessToken = getToken(code);
//
////      2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
        LoginKakaoRequestDto kakaoUserInfo = getKakaoUserInfo(accessToken);*/
        LoginKakaoRequestDto kakaoUserInfo = getKakaoUserInfo(code);

        // 3. 필요시에 회원가입
        User kakaoUser = registerKakaoUser(kakaoUserInfo);

        // 4. JWT 토큰 반환
        String createToken = jwtUtil.createToken(kakaoUser.getUserId(), kakaoUser.getRole());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, createToken);

        return new KakaoResponseDto(SuccessCode.LOG_IN);
    }

    // 1. "인가 코드"로 "액세스 토큰" 요청
    private String getToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "84d4cb0f0b38976fab6edba825421247");
/*        body.add("redirect_uri", "http://3.38.190.107:8080/api/user/kakao/callback");*/
        body.add("redirect_uri", "http://localhost:3000/api/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 2. 토큰으로 카카오 API 호출 : "액세스 토큰"으로 "카카오 사용자 정보" 가져오기
    private LoginKakaoRequestDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        String nickname = RandomStringUtils.random(6, true, true);                         // 닉네임 랜덤 생성
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String email = jsonNode.get("kakao_account")
                .get("email").asText();

        return new LoginKakaoRequestDto(email, nickname);
    }


    // 3. 필요시에 회원가입
    private User registerKakaoUser(LoginKakaoRequestDto kakaoUserInfo) {
        String nickname = RandomStringUtils.random(6, true, true);
        String kakaoId = kakaoUserInfo.getUserId();                                                         // DB 에 중복된 Kakao Id 가 있는지 확인
        User kakaoUser = userRepository.findByUserId(kakaoId)
                .orElse(null);
        if (kakaoUser != null) {                                                                            // 카카오 사용자 email 동일한 email 가진 회원이 있는지 확인
            User sameEmailUser = userRepository.findByUserId(kakaoUser.getUserId()).orElse(null);
            sameEmailUser.updateSignupType(signUpType);
        } else{
            // 신규 회원가입
            // password: random UUID
            String password = UUID.randomUUID().toString();
            String encodedPassword = passwordEncoder.encode(password);

            kakaoUser = new User(kakaoId, encodedPassword, nickname, signUpType, UserRoleEnum.USER);
        }
        userRepository.save(kakaoUser);

        return kakaoUser;
    }
}
