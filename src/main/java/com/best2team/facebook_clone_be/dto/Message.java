package com.best2team.facebook_clone_be.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Message {
    private String message1;

    @Builder
    public Message(String message1) {
        this.message1 = message1;

    }

}