package com.pulbatte.pulbatte.post.dto;

import com.pulbatte.pulbatte.comment.dto.CommentResponseDto;
import com.pulbatte.pulbatte.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private String title;
    private String content;
    private String posterImage;
    private String nickname;
    private int category;
    private Long likeCnt;
    private Long commentCnt;
    private String image;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();

    // 게시글 전체 출력
    public PostResponseDto(Post post ,Long likeCnt,Long commentCnt ,String image){
        this.id = post.getId();                     // 게시글 아이디
        this.title = post.getTitle();               // 제목
        this.content = post.getContent();           // 내용
        this.nickname = post.getNickname();         // 작성자 닉네임
        this.image = image;                         // 이미지 url
        this.likeCnt = likeCnt;                     // 좋아요 수
        this.commentCnt=commentCnt;                 // 댓글 수
        this.category = post.getCategory();         // 게시글 카테고리
        this.createdAt = post.getCreatedAt();       // 게시글 작성 시간
        this.modifiedAt = post.getModifiedAt();     // 게시글 수정 시간
    }
    // 게시글 수정 Response
    public PostResponseDto(Post post ,List<CommentResponseDto> commentList, String image){
        this.id = post.getId();                     // 게시글 아이디
        this.title = post.getTitle();               // 제목
        this.content = post.getContent();           // 내용
        this.nickname = post.getNickname();         // 작성자 닉네임
        this.image = image;                         // 이미지 url
        this.category = post.getCategory();         // 게시글 카테고리
        this.createdAt = post.getCreatedAt();       // 게시글 작성 시간
        this.modifiedAt = post.getModifiedAt();     // 게시글 수정 시간
        this.commentList = commentList;             // 댓글 리스트
    }
    // 게시글 상세 조회
    public PostResponseDto(Post post ,List<CommentResponseDto> commentList, String image,Long likeCnt,Long commentCnt,String posterImage){
        this.id = post.getId();                             // 게시글 아이디
        this.title = post.getTitle();                       // 제목
        this.content = post.getContent();                   // 내용
        this.posterImage = posterImage;                     // 작성자 프로필 사진 url
        this.nickname = post.getNickname();                 // 작성자 닉네임
        this.image = image;                                 // 이미지 url
        this.likeCnt = likeCnt;                             // 좋아요 수
        this.commentCnt = commentCnt;                       // 댓글 수
        this.category = post.getCategory();                 // 게시글 카테고리
        this.createdAt = post.getCreatedAt();               // 게시글 작성 시간
        this.modifiedAt = post.getModifiedAt();             // 게시글 수정 시간
        this.commentList = commentList;                     // 댓글 리스트
    }
}
