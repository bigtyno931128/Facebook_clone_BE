package com.best2team.facebook_clone_be.controller;

import com.best2team.facebook_clone_be.dto.ChatRequestDto;
import com.best2team.facebook_clone_be.dto.ChatRoomDto;
import com.best2team.facebook_clone_be.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomRepository chatRoomRepository;

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    public List<ChatRoomDto> room() {
        return chatRoomRepository.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    public ChatRoomDto createRoom(@RequestBody ChatRequestDto chatRequestDto) {
        System.out.println(chatRequestDto);
        return chatRoomRepository.createChatRoom(chatRequestDto);
    }

    // 특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    public ChatRoomDto roomInfo(@PathVariable String roomId) {
        return chatRoomRepository.findRoomById(roomId);
    }
}