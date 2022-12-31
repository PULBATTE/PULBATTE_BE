package com.pulbatte.pulbatte.post.service;

import com.pulbatte.pulbatte.comment.dto.CommentResponseDto;
import com.pulbatte.pulbatte.comment.entity.Comment;
import com.pulbatte.pulbatte.comment.repository.CommentRepository;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.post.dto.PostRequestDto;
import com.pulbatte.pulbatte.post.dto.PostResponseDto;
import com.pulbatte.pulbatte.post.entity.Post;
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

    //게시글 생성
    public PostResponseDto createBoard(PostRequestDto requestDto, User user, MultipartFile multipartFile) throws IOException {
        /*String image = null;
        if (!multipartFile.isEmpty()) {
            image = s3Uploader.upload(multipartFile, "static");
        }*/
        Post post = postRepository.save(new Post(requestDto, user/*, image*/));

        return new PostResponseDto(post/*, image*/);
    }

    //게시글 수정
    @Transactional
    public PostResponseDto updateBoard(User user, Long id, PostRequestDto requestDto, MultipartFile multipartFile) throws IOException {
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
        /*if (InterestTag.valueOfInterestTag(requestDto.getCategory()) == null) {
            throw new CustomException(ErrorCode.NO_EXIST_LOCAL);
        }
        String image = null;
        if (!multipartFile.isEmpty()) { // 사진이 수정된 경우
            image = (s3Uploader.upload(multipartFile, "static")); // 새로들어온 이미지 s3 저장

            Post board1 = postRepository.findById(id).orElseThrow();

            s3Uploader.delete(board1.getImage(), "static");

            board1.update(image);

        }*/
        return new PostResponseDto(post, commentList/*, image*/);
    }
    //게시글 전체 출력 페이징 처리
    @Transactional(readOnly = true)
    public Page<PostResponseDto> getListBoards(Pageable pageable) {
        Page<Post> boardList = postRepository.findAllByOrderByCreatedAtDesc(pageable);
        List<PostResponseDto> boardResponseDto = new ArrayList<>();

        for (Post post : boardList) {
            /*Long likeCnt = likeRepository.likeCnt(post.getId());*/
            /*Long commentCnt = commentRepository.countByBoardId(post.getId());*/
            /*String image = post.getImage();*/
            List<CommentResponseDto> commentList = new ArrayList<>();
            for (Comment comment : post.getCommentList()) {
                commentList.add(new CommentResponseDto(comment));
            }
            boardResponseDto.add(new PostResponseDto(post, commentList/*, image, likeCnt, commentCnt*/));
        }
        Page<PostResponseDto> page = new PageImpl<>(boardResponseDto);
        return page;
    }
    //게시글 상세 조회
    @Transactional(readOnly = true)
    public PostResponseDto getBoard(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NO_BOARD_FOUND)
        );

        /*Long likeCnt = likeRepository.likeCnt(post.getId());
        String image = post.getImage();*/

        // 댓글 리스트 생성 + 사용자/댓글작성자 일치 여부 확인
        List<Comment> commentList = commentRepository.findAllByPostId(id);
        List<CommentResponseDto> commentListResponseDto = new ArrayList<>();

        /*// 사용자가 좋아요 누른 사용자가 일치한지 체크
        boolean boardlike;
        if (likeRepository.findByBoardIdAndUserId(id, user.getId()).isPresent()) {
            boardlike = true;
        } else {
            boardlike = false;
        }

        // 해당 게시물에 사용자가 작성한 댓글 포함 여부
        boolean commentIncluding;
        if (commentRepository.findAllByBoardId(id).equals(user.getUserId())){
            commentIncluding = true;
        } else {
            commentIncluding = false;
        }*/

        return new PostResponseDto(post, commentListResponseDto/*, image, likeCnt, boardlike, commentIncluding*/);
    }


}
