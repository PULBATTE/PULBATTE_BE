package com.pulbatte.pulbatte.mypage.dto;

import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;


@Getter
public class MypageResponseDto {
    private String nickname;
    private String profileImage;

    public MypageResponseDto(User user){
        this.nickname = user.getNickname();
        this.profileImage = user.getProfileImage();
    }
}
