package com.best2team.facebook_clone_be.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatRequestDto {
    private String publisher;
    private String subscriber;
}
