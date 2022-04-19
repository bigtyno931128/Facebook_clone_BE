package com.best2team.facebook_clone_be.dto;

import java.util.List;

public class LoginUserListDto {
    List<String> userList;

    public LoginUserListDto(List<String> userList){
        this.userList = userList;
    }
}
