package com.best2team.facebook_clone_be.websocket.controller;

import com.best2team.facebook_clone_be.dto.RoomRequestDto;
import com.best2team.facebook_clone_be.websocket.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/chat/room")
    public RoomRequestDto insertRoom(@RequestBody RoomRequestDto roomRequestDto){
        Long min = Math.min(roomRequestDto.getSender(), roomRequestDto.getRecevier());
        Long max = Math.max(roomRequestDto.getRecevier(), roomRequestDto.getSender());
        roomRequestDto.setRoomId(min+"to"+max);
        chatRoomRepository.enterChatRoom(min+"to"+max);
        return roomRequestDto;
    }
}
