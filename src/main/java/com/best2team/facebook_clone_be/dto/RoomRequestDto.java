package com.best2team.facebook_clone_be.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoomRequestDto {
    private Long sender;
    private Long recevier;
    private String roomId;
}
