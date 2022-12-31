package com.pulbatte.pulbatte.comment.dto;

import com.pulbatte.pulbatte.comment.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId;
    private Long postId;
    private String nickname;
    private String comment;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;


    public CommentResponseDto(Comment comment) {
        this.postId = comment.getPost().getId();
        this.commentId = comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.comment = comment.getContent();
        this.modifiedAt = comment.getModifiedAt();
        this.createdAt = comment.getModifiedAt();
    }
}
