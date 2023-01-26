package com.pulbatte.pulbatte.mypage.controller;

import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.mypage.dto.MypageResponseDto;
import com.pulbatte.pulbatte.mypage.dto.StringDto;
import com.pulbatte.pulbatte.mypage.service.MypageService;
import com.pulbatte.pulbatte.post.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;


    // 마이페이지 게시글
    @GetMapping("/mypost")
    public Page<PostResponseDto> getMypage(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PageableDefault(size = 10) Pageable pageable
    ){
        return mypageService.getMypage(userDetails.getUser(), pageable);
    }

    // 마이페이지 유저 프로필
    @GetMapping("/myprofile")
    public MypageResponseDto getProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return mypageService.getProfile(userDetails.getUser());
    }

    // 닉네임 중복 확인
    @PostMapping("/nickDupleCheck")
    public MsgResponseDto nickDupleCheck(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody StringDto request
    ) {
        mypageService.nickDupleCheck(userDetails.getUser(),request);
        return new MsgResponseDto(SuccessCode.CHECK_NICKNAME);
    }

    // 프로필 수정
    @PutMapping("/profile")
    public MsgResponseDto updateProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails ,
            @RequestPart String request,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile
    ) throws IOException {
        mypageService.updateProfil(userDetails.getUser(), request, multipartFile);
        return new MsgResponseDto(SuccessCode.UPDATE_PROFILE);
    }

    @PutMapping("/profilename")
    public MsgResponseDto updateProfilename(
            @AuthenticationPrincipal UserDetailsImpl userDetails ,
            @RequestPart String request
    ){
        mypageService.updateProfilename(userDetails.getUser(), request);
        return new MsgResponseDto(SuccessCode.UPDATE_PROFILE);
    }

    // 프로필 이미지 확인
    @PutMapping("/profileImage")
    public String getProfileImage(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        return userDetails.getUser().getProfileImage();
    }

    // 회원 탈퇴
    @DeleteMapping("/deleteuser")
    public MsgResponseDto deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        mypageService.deleteUser(userDetails.getUser());
            return new MsgResponseDto(SuccessCode.DELETE_USER);
    }

}
