package com.best2team.facebook_clone_be.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LikeDto {
    private Long userId;
    private Long postId;
}
