package com.pulbatte.pulbatte.comment.dto;

import com.pulbatte.pulbatte.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long postId;
    private String profileImage;
    private String nickname;
    private String comment;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Long parentId;
    private List<CommentResponseDto> children= new ArrayList<>();

    //대댓글 Response
    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();                       // 댓글 id
        this.postId = comment.getPost().getId();                // 게시글 id
        this.profileImage = comment.getUser().getProfileImage(); // 작성자 프로필 사진
        this.nickname = comment.getUser().getNickname();        // 작성자 닉네임
        this.comment = comment.getContent();                    // 댓글 작성 내용
        this.parentId = comment.getParent().getId();            // 부모 댓글 id
        this.modifiedAt = comment.getModifiedAt();              // 수정 시간
        this.createdAt = comment.getModifiedAt();               // 작성 시간
    }
    //댓글 ResponseDto
    public CommentResponseDto(Comment comment, List<CommentResponseDto> commentResponseDtoList){
        this.commentId = comment.getId();                       // 댓글 id
        this.postId = comment.getPost().getId();                // 게시글 id
        this.profileImage = comment.getUser().getProfileImage(); // 작성자 프로필 사진
        this.nickname = comment.getUser().getNickname();        // 작성자 닉네임
        this.comment = comment.getContent();                    // 댓글 작성 내용
        this.createdAt = comment.getCreatedAt();                // 작성 시간
        this.modifiedAt = comment.getModifiedAt();              // 수정 시간
        this.parentId = 0L;                                     // 부모 댓글 id
        this.children = commentResponseDtoList;                 // 대댓글
    }
}
