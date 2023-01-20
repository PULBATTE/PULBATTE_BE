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
    private String content;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Long parentId;
    private List<CommentResponseDto> replyList = new ArrayList<>();

    //대댓글 Response
    public CommentResponseDto(Comment content) {
        this.commentId = content.getId();                       // 댓글 id
        this.postId = content.getPost().getId();                // 게시글 id
        this.profileImage = content.getUser().getProfileImage(); // 작성자 프로필 사진
        this.nickname = content.getUser().getNickname();        // 작성자 닉네임
        this.content = content.getContent();                    // 댓글 작성 내용
        this.parentId = content.getParent().getId();            // 부모 댓글 id
        this.modifiedAt = content.getModifiedAt();              // 수정 시간
        this.createdAt = content.getModifiedAt();               // 작성 시간
    }
    //댓글 ResponseDto
    public CommentResponseDto(Comment content, List<CommentResponseDto> commentResponseDtoList){
        this.commentId = content.getId();                       // 댓글 id
        this.postId = content.getPost().getId();                // 게시글 id
        this.profileImage = content.getUser().getProfileImage(); // 작성자 프로필 사진
        this.nickname = content.getUser().getNickname();        // 작성자 닉네임
        this.content = content.getContent();                    // 댓글 작성 내용
        this.createdAt = content.getCreatedAt();                // 작성 시간
        this.modifiedAt = content.getModifiedAt();              // 수정 시간
        this.parentId = 0L;                                     // 부모 댓글 id
        this.replyList = commentResponseDtoList;                 // 대댓글
    }
}
