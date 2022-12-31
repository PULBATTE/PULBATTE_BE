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
    private String nickname;
    private String comment;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private Long parentId;
    private List<CommentResponseDto> children= new ArrayList<>();


    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.nickname = comment.getUser().getNickname();
        this.comment = comment.getContent();
        this.modifiedAt = comment.getModifiedAt();
        this.createdAt = comment.getModifiedAt();
    }

    public CommentResponseDto(Comment comment, Long commentId) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.nickname = comment.getUser().getNickname();
        this.comment = comment.getContent();
        this.parentId = commentId;
        this.modifiedAt = comment.getModifiedAt();
        this.createdAt = comment.getModifiedAt();
    }

    public CommentResponseDto(Comment comment, List<CommentResponseDto> commentResponseDtoList){
        this.commentId = comment.getId();                      // 댓글 id
        this.nickname = comment.getUser().getNickname();                // 작성자명
        this.comment = comment.getContent();                // 작성내용
        this.createdAt = comment.getCreatedAt();               // 작성시간
        this.modifiedAt = comment.getModifiedAt();              // 수정시간
        this.children = commentResponseDtoList;                // 대댓글
    }
}
