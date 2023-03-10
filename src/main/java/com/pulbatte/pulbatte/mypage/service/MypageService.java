package com.pulbatte.pulbatte.mypage.service;

import com.pulbatte.pulbatte.comment.repository.CommentRepository;
import com.pulbatte.pulbatte.global.S3Uploader;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.mypage.dto.MypageResponseDto;
import com.pulbatte.pulbatte.mypage.dto.StringDto;
import com.pulbatte.pulbatte.post.dto.PostResponseDto;
import com.pulbatte.pulbatte.post.entity.Post;
import com.pulbatte.pulbatte.post.repository.LikeRepository;
import com.pulbatte.pulbatte.post.repository.PostRepository;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;


    // 마이페이지 조회
    public Page<PostResponseDto> getMypage(User user, Pageable pageable) {
        Page<Post> postList = postRepository.findAllByUserId(user.getId(), pageable);
        Page<PostResponseDto> paginList = postList.map(
                post -> new PostResponseDto(
                        post,
                        (long) post.getPostLike().size(),
                        (long) post.getCommentList().size(),
                        post.getImage()
                ));
        return paginList;
    }

    // 닉네임 중복 확인
    public void nickDupleCheck(User user, StringDto stringDto) {
        if (userRepository.findByNickname(stringDto.getNickname()).isPresent() && !user.getNickname().equals(stringDto.getNickname())) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USERNAME);
        }
    }

    // 프로필 수정
    @Transactional
    public void updateProfil(User user, StringDto string, MultipartFile multipartFile) throws IOException {
        User changeUser = userRepository.findByUserId(user.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.ALREADY_EXIST_USERNAME)
        );
        String image = "";
        if (!multipartFile.isEmpty()) {
            image = s3Uploader.upload(multipartFile, "profile");
        }
        changeUser.updateProfile(image);
        changeUser.updateNickname(string.getNickname());
    }

    @Transactional
    public void updateProfileName(User user, StringDto request) {
        User changeUser = userRepository.findByUserId(user.getUserId()).orElseThrow(
                () -> new CustomException(ErrorCode.ALREADY_EXIST_USERNAME)
        );
        changeUser.updateNickname(request.getNickname());
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(User user) {
        s3Uploader.delete(user.getProfileImage(), "profile");
        userRepository.deleteById(user.getId());
    }

    public MypageResponseDto getProfile(User user) {
        return new MypageResponseDto(user);
    }


}
