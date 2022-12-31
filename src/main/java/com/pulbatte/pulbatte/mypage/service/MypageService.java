package com.pulbatte.pulbatte.mypage.service;

import com.pulbatte.pulbatte.global.S3Uploader;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.mypage.dto.StringDto;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final S3Uploader s3Uploader;

    public void nickDupleCheck(User user,StringDto stringDto) {
        if(userRepository.findByNickname(stringDto.getNickname()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USERNAME);
        }
    }

    @Transactional
    public void nickName(User user, StringDto stringDto) {
        User changeUser = userRepository.findByUserId(user.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.ALREADY_EXIST_USERNAME)
        );
        changeUser.updateNickname(stringDto.getNickname());
    }

    @Transactional
    public void profileImage(User user, MultipartFile multipartFile) throws IOException {
        String image = s3Uploader.upload(multipartFile,"static");
        User changeimageuser = userRepository.findByUserId(user.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.ALREADY_EXIST_USERNAME)
        );
        changeimageuser.updateProfile(image);
    }

    @Transactional
    public void deleteUser(User user) {
        s3Uploader.delete(user.getProfileImage(), "static");
        userRepository.deleteById(user.getId());
    }
}
