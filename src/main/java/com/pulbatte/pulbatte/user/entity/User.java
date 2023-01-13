package com.pulbatte.pulbatte.user.entity;

import com.pulbatte.pulbatte.bignner.entity.BeginnerGraph;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "user")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userId;                                  // 사용자 Id
    @Column(nullable = false)
    private String password;                                // 사용자 Password
    @Column(nullable = true)
    private String nickname;                                // 사용자 닉네임 (랜덤 난수로 닉네임 생성하여 저장)
    @Column(nullable = true)
    private String profileImage;                            // 프로필 사진 Url (S3 Path)
    @Column(nullable = false)
    private int signUpType;                                 // 회원가입 타입 (카카오 Or 이메일 로그인)
    @Column(nullable = true)
    private String testResult;                              // 식집사 테스트 결과
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;                              // 유저 권한(일반, 관리자)


    // 일반 User용 생성자
    public User(String userId, String password, String nickname,int signUpType, UserRoleEnum role) {
        this.userId = userId;               // id(email 형식)
        this.password = password;           // 비밀번호
        this.nickname = nickname;           // 닉네임 (난수)
        this.signUpType = signUpType;       // 회원가입 타입
        this.role = role;                   // 권한
    }

    // 프로필 사진 업데이트
    public void updateProfile(String profileImage){
        this.profileImage = profileImage;
}
    // 닉네임 업데이트
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }
    // 로그인 타입 지정
    public void updateSignupType(int signUpType) {
        this.signUpType = signUpType;
    }
    // 식집사 테스트 결과 업데이트
    public void updateTestResult(String testResult){
        this.testResult = testResult;
    }
}
