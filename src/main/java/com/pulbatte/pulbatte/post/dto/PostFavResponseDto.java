package com.pulbatte.pulbatte.post.dto;

import com.pulbatte.pulbatte.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostFavResponseDto {
    private Long id;
    private String title;
    private String image;

    public PostFavResponseDto(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.image = post.getImage();

    }
}
