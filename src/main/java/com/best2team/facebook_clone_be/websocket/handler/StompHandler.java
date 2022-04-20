package com.best2team.facebook_clone_be.websocket.handler;

import com.best2team.facebook_clone_be.utils.SocketUtil;
import com.best2team.facebook_clone_be.websocket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Optional;

@RequiredArgsConstructor
public class StompHandler implements ChannelInterceptor {
    private final ChatRoomRepository chatRoomRepository;

    // websocket을 통해 들어온 요청이 처리 되기전 실행된다.
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.SUBSCRIBE == accessor.getCommand()) { // 채팅룸 구독요청
            // header정보에서 구독 destination정보를 얻고, roomId를 추출한다.
            String roomId = SocketUtil.getRoomId(Optional.ofNullable((String) message.getHeaders().get("simpDestination")).orElse("InvalidRoomId"));
            if(!roomId.contains("to")) {
                // 채팅방에 들어온 클라이언트 sessionId를 roomId와 맵핑해 놓는다.
                String sessionId = (String) message.getHeaders().get("simpSessionId");
                chatRoomRepository.saveLoginUser(sessionId, roomId);
            }
        } else if (StompCommand.DISCONNECT == accessor.getCommand()) { // Websocket 연결 종료
            //sessindId를 통해 현재 접속중인 유저 목록에서 삭제 시킨다.
            String sessionId = (String) message.getHeaders().get("simpSessionId");
            chatRoomRepository.deleteLoginUser(sessionId);

        }
        return message;
    }
}