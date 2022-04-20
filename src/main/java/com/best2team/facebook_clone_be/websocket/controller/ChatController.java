package com.best2team.facebook_clone_be.websocket.controller;

import com.best2team.facebook_clone_be.websocket.model.ChatMessage;
import com.best2team.facebook_clone_be.websocket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    public void message(ChatMessage message) {
        System.out.println(message);
        Long min = Math.min(message.getMessageSender(), message.getMessageRecevier());
        Long max = Math.max(message.getMessageRecevier(), message.getMessageSender());
        message.setRoomId(min+"to"+max);

        chatRoomRepository.saveMessage(message);
        // recevier 알림 발행
        redisPublisher.publish(chatRoomRepository.getTopic(Long.toString(message.getMessageRecevier())), message);
        // Websocket에 발행된 메시지를 redis로 발행한다(publish)
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }
}
