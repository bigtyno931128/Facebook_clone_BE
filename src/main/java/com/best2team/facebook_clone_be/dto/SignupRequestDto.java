package com.best2team.facebook_clone_be.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SignupRequestDto {
    private String userName;
    private String userEmail;
    private String password;
}
