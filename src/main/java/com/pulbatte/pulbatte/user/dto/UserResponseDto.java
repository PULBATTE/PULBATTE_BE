package com.pulbatte.pulbatte.user.dto;

import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String profileImage;
    private String nickNmae;

    public UserResponseDto(User user){
        this.userId = user.getId();
        this.profileImage = user.getProfileImage();
        this.nickNmae = user.getNickname();
    }
}
