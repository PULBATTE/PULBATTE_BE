package com.pulbatte.pulbatte.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {

    @NotBlank(message = "이메일에 공백이 있거나 값을 입력하지 않았습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")
    private String userId;

    @NotBlank(message = "비밀번호에 공백이 있거나 값을 입력하지 않았습니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,15}$", message = "비밀번호는 최소 8자 이상, 15자 이하이며 공백을 제외한  특수문자($@$!%*#?&), 알파벳 대소문자(a~z, A~Z), 숫자(0~9)이어야 합니다.")
    private String password;

    private boolean admin = false;

    private String adminToken = "";
}