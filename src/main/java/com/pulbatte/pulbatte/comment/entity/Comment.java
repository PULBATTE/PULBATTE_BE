package com.pulbatte.pulbatte.comment.entity;

import com.pulbatte.pulbatte.comment.dto.CommentRequestDto;
import com.pulbatte.pulbatte.global.entity.TimeStamped;
import com.pulbatte.pulbatte.post.entity.Post;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @ManyToOne
    @JoinColumn(name = "Post_Id", nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "USERS_ID")
    private User user;

    public Comment(CommentRequestDto commentRequestDto, Post post, User user){
        this.content = commentRequestDto.getComment();
        this.userId = user.getUserId();
        this.post = post;
        this.user = user;
    }


}
