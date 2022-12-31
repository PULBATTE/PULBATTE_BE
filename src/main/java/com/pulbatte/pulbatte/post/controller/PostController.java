package com.pulbatte.pulbatte.post.controller;

import com.pulbatte.pulbatte.global.security.UserDetailsImpl;
import com.pulbatte.pulbatte.post.dto.PostRequestDto;
import com.pulbatte.pulbatte.post.dto.PostResponseDto;
import com.pulbatte.pulbatte.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    //게시글 생성
    @PostMapping(value = "/post", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public PostResponseDto createBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                       @RequestPart PostRequestDto request,
                                       @RequestPart("image") MultipartFile multipartFile) throws IOException {
        return postService.createBoard(request, userDetails.getUser(), multipartFile);
    }
    //게시글 수정
    @PutMapping("/boards/{boardId}")
    public PostResponseDto updateBoard(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @PathVariable Long boardId,
                                        @RequestPart PostRequestDto request,
                                        @RequestPart(value = "image" ,required = false) MultipartFile multipartFile) throws IOException {
        return postService.updateBoard(userDetails.getUser(), boardId, request, multipartFile);
    }

}
