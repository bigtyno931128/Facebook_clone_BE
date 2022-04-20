package com.best2team.facebook_clone_be.websocket.model;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public class ChatMessage implements Serializable {

    private static final long serialVersionUID = 6494678977089006639L;

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private Long messageSender; // 메시지 보낸사람
    private Long messageRecevier; // 메세지 받는 사람
    private String roomId; // 방번호
    private String message; // 메시지
}
