package com.best2team.facebook_clone_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostEditResponseDto {
    private Long postId;
    private String content;
    private String postImageUrl;

    public PostEditResponseDto(Long postid, String content, String postImageUrl) {
        this.postId=postid;
        this.content=content;
        this.postImageUrl=postImageUrl;
    }
}
