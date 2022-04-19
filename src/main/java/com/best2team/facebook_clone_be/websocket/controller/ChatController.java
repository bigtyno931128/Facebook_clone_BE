package com.best2team.facebook_clone_be.websocket.controller;

import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.websocket.model.ChatMessage;
import com.best2team.facebook_clone_be.websocket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long min = Math.min(message.getSender(), message.getRecevier());
        Long max = Math.max(message.getRecevier(), message.getSender());
        message.setRoomId(min+"to"+max);

        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            chatRoomRepository.enterChatRoom(message, userDetails);
        }

        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        chatRoomRepository.saveMessage(message, userDetails);
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }
}
