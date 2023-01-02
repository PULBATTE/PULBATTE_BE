package com.pulbatte.pulbatte.mypage.controller;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.mypage.dto.StringDto;
import com.pulbatte.pulbatte.mypage.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // 닉네임 중복 확인
    @PostMapping("/nickDupleCheck")
    public MsgResponseDto nickDupleCheck(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody StringDto stringDto) {
        mypageService.nickDupleCheck(userDetails.getUser(),stringDto);
        return new MsgResponseDto(SuccessCode.CHECK_NICKNAME);
    }

    // 닉네임 수정
    @PutMapping("/nickname")
    public MsgResponseDto nickname(
            @AuthenticationPrincipal UserDetailsImpl userDetails ,
            @RequestBody StringDto stringDto){
        mypageService.nickName(userDetails.getUser(), stringDto);
        return new MsgResponseDto(SuccessCode.UPLOAD_NICKNAME);
    }

    // 프로필 이미지 등록
    @PutMapping("/profileImage")
    public MsgResponseDto profileImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart(value = "image",required = false) MultipartFile multipartFile) throws IOException {
        mypageService.profileImage(userDetails.getUser(),multipartFile);
        return new MsgResponseDto(SuccessCode.UPLOAD_PROFILE);
    }

    // 회원 탈퇴
    @DeleteMapping("/deleteuser")
    public MsgResponseDto deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails){
        mypageService.deleteUser(userDetails.getUser());
            return new MsgResponseDto(SuccessCode.DELETE_USER);
    }
    
}
