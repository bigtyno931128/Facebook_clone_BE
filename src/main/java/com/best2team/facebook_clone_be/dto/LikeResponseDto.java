package com.best2team.facebook_clone_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LikeResponseDto {
    private Boolean msg;

    public LikeResponseDto(Boolean msg) {
        this.msg=msg;
    }
}
