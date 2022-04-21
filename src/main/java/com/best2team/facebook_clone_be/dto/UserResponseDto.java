package com.best2team.facebook_clone_be.dto;

import com.best2team.facebook_clone_be.model.User;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {
    private Long userId;
    private String userEmail;
    private String userName;
    private String userImage;
    private boolean isLogin;

    //islogin
    public UserResponseDto(UserDetailsImpl userDetails){
        this.userId = userDetails.getId();
        this.userEmail = userDetails.getUser().getUserEmail();
        this.userName = userDetails.getUser().getUserName();
        this.isLogin = userDetails.getUser().isLogin();
        try {
            this.userImage = userDetails.getUser().getUserImage().getImageUrl();
        }catch (NullPointerException e){
            this.userImage = "없음";
        }

    }

    public UserResponseDto(User user){
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.userImage = user.getUserImage().getImageUrl();
    }

    //userImage
    public UserResponseDto(String imageUrl) {
        this.userImage = imageUrl;
    }


}
