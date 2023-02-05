package com.pulbatte.pulbatte.global.service;

import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.dto.RequestToken;
import com.pulbatte.pulbatte.global.entity.RefreshToken;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.jwt.JwtUtil;
import com.pulbatte.pulbatte.global.jwt.TokenDto;
import com.pulbatte.pulbatte.global.repository.RefreshTokenRepository;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;

    // 토큰 재발행
    public TokenDto reFreshToken(/*User user,*/String authorization,HttpServletResponse response, RequestToken requestToken){
        TokenDto tokenDto;
        User user = userRepository.findByUserId(requestToken.getUserEmail()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_EXIST_USER)
        );
        RefreshToken refreshToken = refreshTokenRepository.findByAccountUserId(user.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.DISMATCH_TOKEN)
        );
        if(refreshToken.getRefreshToken().equals(requestToken.getRefreshToken())){
            response.addHeader(JwtUtil.ACCESS_TOKEN, jwtUtil.createToken(user.getUserId()));
            tokenDto = jwtUtil.createAllToken(user.getUserId());
            refreshTokenRepository.save(refreshToken.updateToken(tokenDto.getRefreshToken(),authorization));
        }else {
            throw new CustomException(ErrorCode.DISMATCH_TOKEN2);
        }
        return tokenDto;
    }
    // 리프레시 토큰 삭제
    @Transactional
    public void deleteToken(User user){
        refreshTokenRepository.deleteByAccountUserId(user.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.NO_EXIST_USER)
        );
    }
}