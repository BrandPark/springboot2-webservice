package com.brandpark.springboot.web.dto;

import com.brandpark.springboot.domain.posts.Posts;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostsResponseDto {
    private String title;
    private String content;

    @Builder
    public PostsResponseDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostsResponseDto(Posts posts) {
        this.title = posts.getTitle();
        this.content = posts.getContent();
    }
}
