package com.pulbatte.pulbatte.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pulbatte.pulbatte.comment.dto.CommentRequestDto;
import com.pulbatte.pulbatte.global.entity.TimeStamped;
import com.pulbatte.pulbatte.post.entity.Post;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Comments")
@Getter
@NoArgsConstructor
public class Comment extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userId;          // 작성자 아이디
    @Column(nullable = false)
    private String content;         // 댓글 내용
    @Column(nullable = false)
    private String nickname;        // 작성자 닉네임
    @ManyToOne
    @JoinColumn(name = "Post_Id", nullable = false)
    private Post post;              // 작성한 게시글
    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private User user;              // 작성한 유저
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parentid")  // 부모 댓글
    private Comment parent;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    private List<Comment> replyList = new ArrayList<>();  // 대댓글 리스트

    // 댓글 작성
    public Comment(CommentRequestDto commentRequestDto, Post post, User user){
        this.content = commentRequestDto.getContent();  // 입력 받은 댓글 내용
        this.userId = user.getUserId();                 // 입력한 userId
        this.nickname = user.getNickname();             // 유저 닉네임
        this.post = post;                               // 게시글 정보
        this.user = user;                               // 유저 정보
    }
    // 대댓글 작성
    public Comment(CommentRequestDto commentRequestDto, Post post, User user,Comment comment){
        this.content = commentRequestDto.getContent();  //입력 받은 댓글 내용
        this.userId = user.getUserId();                 // 입력한 userId
        this.nickname = user.getNickname();             // 유저 닉네임
        this.post = post;                               // 게시글 정보
        this.user = user;                               // 유저 정보
        this.parent = comment;                          // 부모 댓글 정보
    }
    // 댓글 수정
    public void update(CommentRequestDto commentRequestDto){
        this.content = commentRequestDto.getContent();  // 댓글 수정 내용
    }
}
