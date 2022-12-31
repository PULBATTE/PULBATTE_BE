package com.pulbatte.pulbatte.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
// Setter 를 사용해도 가능은 하나 @NoArgsConstructor 를 사용해도 된다.
public class UserRequestDto {
    private String userId;
    private String password;
}