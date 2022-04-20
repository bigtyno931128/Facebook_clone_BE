package com.best2team.facebook_clone_be.websocket.controller;

import com.best2team.facebook_clone_be.websocket.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatLogController {
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatLogController(ChatRoomRepository chatRoomRepository){
        this.chatRoomRepository = chatRoomRepository;
    }

    @GetMapping("/chat/log/{roomid}")
    public void findChatLog(@PathVariable String roomid){
        chatRoomRepository.findMessage(roomid);
    }

}
