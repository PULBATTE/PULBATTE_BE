package com.pulbatte.pulbatte.post.entity;

import com.pulbatte.pulbatte.comment.entity.Comment;
import com.pulbatte.pulbatte.global.entity.TimeStamped;
import com.pulbatte.pulbatte.post.dto.PostRequestDto;
import com.pulbatte.pulbatte.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity(name = "post")
@Getter
@NoArgsConstructor
public class Post extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private int category;
    /*@Column(nullable = false)
    private String image;*/
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private List<Comment> commentList;

    public Post(PostRequestDto postRequestDto , User user/*, String image*/){
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
        this.category = postRequestDto.getCategory();
        this.nickname = user.getNickname();
        /*this.image = image;*/

    }
    public void update(PostRequestDto requestDto){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.category = requestDto.getCategory();
    }

}
