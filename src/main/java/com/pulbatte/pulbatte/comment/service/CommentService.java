package com.pulbatte.pulbatte.comment.service;

import com.pulbatte.pulbatte.comment.dto.CommentRequestDto;
import com.pulbatte.pulbatte.comment.dto.CommentResponseDto;
import com.pulbatte.pulbatte.comment.entity.Comment;
import com.pulbatte.pulbatte.comment.repository.CommentRepository;
import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.CustomException;
import com.pulbatte.pulbatte.global.exception.ErrorCode;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.post.entity.Post;
import com.pulbatte.pulbatte.post.repository.PostRepository;
import com.pulbatte.pulbatte.user.entity.User;
import com.pulbatte.pulbatte.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    //댓글 작성
    public CommentResponseDto saveComment(Long id, Long commentId, CommentRequestDto commentRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_BOARD_FOUND));

        Comment comment;
        if (commentId == 0) {                                                                               //댓글 id가 0 일 때 > 부모 댓글(댓글)로 취급
            comment = commentRepository.save(new Comment(commentRequestDto, post, user));                   //부모 댓글로 저장
        } else {                                                                                            //댓글 id가 0이 아닐 때 > 자식 댓글(대댓글)로 취급
            Comment childComment = commentRepository.findById(commentId).orElseThrow(
                    () -> new CustomException(ErrorCode.NO_EXIST_COMMENT)
            );
            if (commentRepository.findByPostAndId(post, commentId).isEmpty()){                              //postId로 찾은 post 정보와 commentId가 둘다 일치하는 댓글이 없는 경우 에러코드 출력
                throw new CustomException(ErrorCode.NO_EXIST_COMMENT);
            }
            comment = commentRepository.save(new Comment(commentRequestDto, post, user, childComment));     //자식 댓글로 저장
        }
        return new CommentResponseDto(comment, commentId);
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id, Long commentId, CommentRequestDto commentRequestDto, User user){
        postRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.NO_BOARD_FOUND));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new CustomException(ErrorCode.NO_EXIST_COMMENT));

        if(comment.getUserId().equals(user.getUserId())){
            comment.update(commentRequestDto);
        }else {
            throw new CustomException(ErrorCode.NO_MODIFY_COMMENT);
        }
        return new CommentResponseDto(comment);
    }

    //댓글 삭제
    @Transactional
    public MsgResponseDto deleteComment(Long id, Long commentId, User user) {
        postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NO_BOARD_FOUND));

        Comment comment;
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            // ADMIN 권한일 때
            comment = commentRepository.findById(commentId).orElseThrow(() -> new CustomException(ErrorCode.NO_EXIST_COMMENT));
        } else {
            // User 권한일 때
            comment = commentRepository.findByIdAndUserId(commentId, user.getUserId()).orElseThrow(() -> new CustomException(ErrorCode.NO_DELETE_COMMENT));
        }

        commentRepository.delete(comment);
        return new MsgResponseDto(SuccessCode.DELETE_COMMENT);
    }
}