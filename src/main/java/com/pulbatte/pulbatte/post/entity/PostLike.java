package com.pulbatte.pulbatte.post.entity;

import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "PostLike")
@Getter
@NoArgsConstructor
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "User_Id", nullable = false)                   // category(n) <-> User(1) 단방향 관계
    private User user;                                                // 좋아요 유저 Id

    @ManyToOne
    @JoinColumn(name = "Post_Id", nullable = false)                  // category(n) <-> Board(1) 단방향 관계
    private Post post;                                              // 좋아요 게시글 Id

    public PostLike(Post post, User user) {
        this.post = post;   // 게시글 정보
        this.user = user;   // 유저 정보
    }
}