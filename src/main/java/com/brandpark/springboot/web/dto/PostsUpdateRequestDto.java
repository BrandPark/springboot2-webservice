package com.brandpark.springboot.web.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PostsUpdateRequestDto {
    private String title;
    private String content;

    @Builder
    private PostsUpdateRequestDto(String title, String content){
        this.title = title;
        this.content = content;
    }
}
