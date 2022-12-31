package com.pulbatte.pulbatte.post.service;

import com.pulbatte.pulbatte.comment.dto.CommentResponseDto;
import com.pulbatte.pulbatte.comment.entity.Comment;
import com.pulbatte.pulbatte.comment.repository.CommentRepository;
import com.pulbatte.pulbatte.global.S3Uploader;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.post.dto.PostRequestDto;
import com.pulbatte.pulbatte.post.dto.PostResponseDto;
import com.pulbatte.pulbatte.post.entity.Post;
import com.pulbatte.pulbatte.post.repository.LikeRepository;
import com.pulbatte.pulbatte.post.repository.PostRepository;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final S3Uploader s3Uploader;

    //게시글 생성
    public PostResponseDto createPost(PostRequestDto requestDto, User user, MultipartFile multipartFile) throws IOException {
        String image = null;
        if (!multipartFile.isEmpty()) {
            image = s3Uploader.upload(multipartFile, "static");
        }
        Post post = postRepository.save(new Post(requestDto, user, image));

        return new PostResponseDto(post, image);
    }
    //게시글 전체 출력 페이징 처리
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getListPosts(Pageable pageable) {
        Page<Post> boardList = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<PostResponseDto> boardResponseDto = new ArrayList<>();

        for (Post post : boardList) {
            String image = post.getImage();
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : post.getCommentList()) {
                commentList.add(new CommentResponseDto(comment));
            }
            boardResponseDto.add(new PostResponseDto(post, commentList, image));
        }
        Page<PostResponseDto> page = new PageImpl<>(boardResponseDto);
        return page;
    }
    //게시글 상세 조회
    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
        );

        Long likeCnt = likeRepository.likeCnt(post.getId());
        String image = post.getImage();

        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
        for (Comment comment : post.getCommentList()) {
            List<CommentResponseDto> childCommentList = new ArrayList<>();
            if(comment.getParent()==null){                                                      //부모 댓글이 없을 경우
                for (Comment childComment : comment.getChildren()){                              //자식 댓글 리스트의 데이터를 childComment에 저장
                    if (id.equals(childComment.getPost().getId())) {                         //childComment의 id와 받아온 id가 일치할 경우(선택 게시글 저장)
                        childCommentList.add(new CommentResponseDto(childComment));             //저장된 자식댓글을 리스트에 저장
                    }
                }
                commentResponseDtoList.add(new CommentResponseDto(comment,childCommentList));   //저장된 데이터를 리스트에
            }
        }


        return new PostResponseDto(post, commentResponseDtoList, image, likeCnt);
    }

    //게시글 수정
    @Transactional
    public PostResponseDto updatePost(User user, Long id, PostRequestDto requestDto, MultipartFile multipartFile) throws IOException {
        Post post;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            post = postRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
            );
        } else {
            post = postRepository.findByIdAndNickname(id, user.getNickname()).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
            );
        }
        post.update(requestDto);

        List<CommentResponseDto> commentList = new ArrayList<>();
        for (Comment comment : post.getCommentList()) {
            commentList.add(new CommentResponseDto(comment));
        }
        String image = null;
        if (!multipartFile.isEmpty()) { // 사진이 수정된 경우
            image = (s3Uploader.upload(multipartFile, "static")); // 새로들어온 이미지 s3 저장

            Post board1 = postRepository.findById(id).orElseThrow();

            s3Uploader.delete(board1.getImage(), "static");

            board1.update(image);

        }
        return new PostResponseDto(post, commentList, image);
    }

    //게시글 삭제
    @Transactional
    public void deletePost(Long id, User user) {
        Post post;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            post = postRepository.findById(id).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
            );
        } else {
            post = postRepository.findByIdAndNickname(id, user.getNickname()).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
            );
        }
        Post post1 = postRepository.findById(id).orElseThrow();

        s3Uploader.delete(post1.getImage(), "static");

        postRepository.delete(post);
    }


}
