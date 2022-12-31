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
    private String userId;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String nickname;
    @ManyToOne
    @JoinColumn(name = "Post_Id", nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private User user;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "parentid")
    private Comment parent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    private List<Comment> children = new ArrayList<>();  // 대댓글 리스트

    public Comment(CommentRequestDto commentRequestDto, Post post, User user){
        this.content = commentRequestDto.getComment();
        this.userId = user.getUserId();
        this.post = post;
        this.user = user;
        this.nickname = user.getNickname();
    }
    public Comment(CommentRequestDto commentRequestDto, Post post, User user,Comment comment){
        this.content = commentRequestDto.getComment();
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.post = post;
        this.user = user;
        this.parent = comment;
    }

    public void update(CommentRequestDto commentRequestDto){
        this.content = commentRequestDto.getComment();
    }


}
