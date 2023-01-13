package com.pulbatte.pulbatte.post.entity;

import com.pulbatte.pulbatte.comment.entity.Comment;
import com.pulbatte.pulbatte.global.entity.TimeStamped;
import com.pulbatte.pulbatte.post.dto.PostRequestDto;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "post")
@Getter
@NoArgsConstructor
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                        // 게시글 아이디
    @Column(nullable = false)
    private String title;                   // 게시글 제목
    @Column(nullable = false)
    private String content;                 // 게시글 내용
    @Column(nullable = false)
    private String nickname;                // 작성자 닉네임
    @Column(nullable = false)
    private int category;                   // 게시글 카테고리
    @Column(nullable = false)
    private String image;                   // 게시글 이미지
    @ManyToOne
    private User user;                      // 게시글 작성자
    @Column
    private LocalDateTime favLikeTime;      // 인기 게시글이 된 시간
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Comment> commentList;      // 댓글 리스트
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<PostLike> postLike;        // 게시글 좋아요


    public Post(PostRequestDto postRequestDto , User user, String image){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = postRequestDto.getCategory();
        this.user = user;
        this.nickname = user.getNickname();
        this.image = image;

    }
    public void update(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
    }
    public void update(String image){
        this.image = image;
    }
    public void updateFaveLikeTime(LocalDateTime favLikeTime){
        this.favLikeTime = favLikeTime;
    }

}
