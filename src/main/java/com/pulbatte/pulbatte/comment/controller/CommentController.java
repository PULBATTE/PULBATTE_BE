package com.pulbatte.pulbatte.comment.controller;

import com.pulbatte.pulbatte.comment.dto.CommentRequestDto;
import com.pulbatte.pulbatte.comment.service.CommentService;
import com.pulbatte.pulbatte.global.MsgResponseDto;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {
    public final CommentService commentService;

    // 댓글 생성
    @PostMapping("/{postId}/comments/{commentId}")
    public MsgResponseDto saveComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.saveComment(postId,commentId,commentRequestDto, userDetailsImpl.getUser());
    }

    //댓글 수정
    @PutMapping("/{postId}/comments/{commentId}")
    public MsgResponseDto updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return commentService.updateComment(postId,commentId,commentRequestDto,userDetailsImpl.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/{postId}/comments/{commentId}")
    public MsgResponseDto deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.deleteComment(postId, commentId, userDetailsImpl.getUser());
    }
}
