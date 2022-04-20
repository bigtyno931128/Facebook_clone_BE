package com.best2team.facebook_clone_be.dto;

import com.best2team.facebook_clone_be.model.UserImage;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
    private String userImageUrl;

    public ProfileResponseDto(UserImage userImage) {
        this.userImageUrl=userImage.getImageUrl();
    }
}
