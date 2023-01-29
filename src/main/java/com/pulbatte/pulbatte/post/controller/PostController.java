package com.pulbatte.pulbatte.post.controller;

import com.pulbatte.pulbatte.global.dto.MsgResponseDto;
import com.pulbatte.pulbatte.global.exception.SuccessCode;
import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.post.dto.PostFavResponseDto;
import com.pulbatte.pulbatte.post.dto.PostRequestDto;
import com.pulbatte.pulbatte.post.dto.PostResponseDto;
import com.pulbatte.pulbatte.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    // 게시글 생성
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDto createPost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestPart PostRequestDto request,
            @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws IOException {
        return postService.createPost(request, userDetails.getUser(), multipartFile);
    }
    // 게시글 전체 조회
    @GetMapping
    public Page<PostResponseDto> getListPosts(
            @PageableDefault(size = 10) Pageable pageable) {
        return postService.getListPosts(pageable);
    }
    // 게시글 태그별 조회
    @GetMapping("/category/{tag}")
    public Page<PostResponseDto> getTagListPosts(
            @PathVariable String tag,
            @PageableDefault(size = 20) Pageable pageable){
        return postService.getTagListPosts(tag,pageable);
    }
    // 인기 게시글 조회
    @GetMapping("/popular")
    public List<PostFavResponseDto> getPopularListPosts(){
        return postService.getPopularListPosts();
    }
    // 게시글 상세 조회(user 전용)
    @GetMapping("/user/{postId}")
    public PostResponseDto getPostUser(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPostUser(postId,userDetails.getUser());
    }
    // 게시글 상세 조회(guest 전용)
    @GetMapping("/guest/{postId}")
    public PostResponseDto getPostGuest(
            @PathVariable Long postId) {
        return postService.getPostGuest(postId);
    }
    // 게시글 수정
    @PutMapping("/{postId}")
    public PostResponseDto updatePost(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long postId,
            @RequestPart PostRequestDto request,
            @RequestPart(value = "image" ,required = false) MultipartFile multipartFile) throws IOException {
        return postService.updatePost(userDetails.getUser(), postId, request, multipartFile);
    }
    // 게시글 삭제
    @DeleteMapping("/{postId}")
    public MsgResponseDto deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.deletePost(postId, userDetails.getUser());
        return new MsgResponseDto(SuccessCode.DELETE_BOARD);
    }
    // 게시글 좋아요, 좋아요 취소
    @PostMapping("/{postId}/postLike")
    public MsgResponseDto saveBoardLike(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.postLike(postId, userDetails.getUser());
    }
}
