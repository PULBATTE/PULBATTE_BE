package com.pulbatte.pulbatte.global.jwt;

import com.pulbatte.pulbatte.global.dto.TokenDto;
import com.pulbatte.pulbatte.global.entity.RefreshToken;
import com.pulbatte.pulbatte.global.repository.RefreshTokenRepository;
import com.pulbatte.pulbatte.global.security.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "Bearer ";
    public static final String ACCESS_TOKEN = "Authorization";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    private static final long TOKEN_TIME = 60 * 60 * 2 *1000L;
    private static final long REFRESH_TOKEN_TIME = 60 * 60 * 12 * 7 * 2 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }
    public String getHeaderToken(HttpServletRequest request,String type){
        return type.equals("Access") ? request.getHeader(ACCESS_TOKEN):request.getHeader(REFRESH_TOKEN);
    }

    // ?????? ??????
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
    public TokenDto createAllToken(String userId){
        return new TokenDto(createToken(userId/*,"Access"*/),createRefreshToken(/*userId,"Refresh"*/),"200", LocalDateTime.now());
    }
    public String createToken(String userId/*, String type*/) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(userId)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }
    public String createRefreshToken(/*String userId, String type*/) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, ???????????? ?????? JWT ?????? ?????????.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, ????????? JWT token ?????????.");
            /*throw new CustomException(ErrorCode.ALREADY_VALIDITY_TOKEN);*/
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, ???????????? ?????? JWT ?????? ?????????.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, ????????? JWT ?????? ?????????.");
        }
        return false;
    }
    //refreshToken ??????
    public Boolean refreshTokenValidation(String token){
        if(!validateToken(token)){
            return false;
        }
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByAccountUserId(token);
        return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken());
    }

    // ???????????? ????????? ?????? ????????????
    // ?????? ???????????? ???????????? ???????????? getBody ??? ????????? ?????? ???????????? ?????? ????????????.
    // ?????? validateToken ???????????? ?????? ????????? ????????? ????????? ?????? ????????? ????????? ?????? ?????? ????????????.
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    // ?????? ?????? ??????
    public Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }
}
