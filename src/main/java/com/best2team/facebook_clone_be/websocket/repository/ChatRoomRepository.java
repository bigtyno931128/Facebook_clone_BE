package com.best2team.facebook_clone_be.websocket.repository;

import com.best2team.facebook_clone_be.model.User;
import com.best2team.facebook_clone_be.repository.UserRepository;
import com.best2team.facebook_clone_be.websocket.controller.RedisSubscriber;
import com.best2team.facebook_clone_be.websocket.model.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {
    // 채팅방(topic)에 발행되는 메시지를 처리할 Listner
    private final RedisMessageListenerContainer redisMessageListener;
    // 구독 처리 서비스
    private final RedisSubscriber redisSubscriber;
    // Redis
    private static final String CHAT_LOGS = "CHAT_LOG";
    private static final String LOGIN_USERS = "LOGIN_USER";
    private List<ChatMessage> messages;
    private final RedisTemplate<String, Object> redisTemplate;
    private ListOperations<String, Object> opsChatLog;
    // 채팅방의 대화 메시지를 발행하기 위한 redis topic 정보. 서버별로 채팅방에 매치되는 topic정보를 Map에 넣어 roomId로 찾을수 있도록 한다.
    private Map<String, ChannelTopic> topics;
    private HashOperations<String,String,String> loginUser;
    private final UserRepository userRepository;

    @Autowired
    public ChatRoomRepository(RedisMessageListenerContainer redisMessageListener, RedisTemplate<String, Object> redisTemplate,
                              UserRepository userRepository, @Lazy RedisSubscriber redisSubscriber) {
        this.redisMessageListener = redisMessageListener;
        this.redisTemplate = redisTemplate;
        this.redisSubscriber = redisSubscriber;
        this.userRepository = userRepository;
    }

    @PostConstruct
    private void init() {
        opsChatLog = redisTemplate.opsForList();
        loginUser = redisTemplate.opsForHash();
        topics = new HashMap<>();
    }

    // 메세지 저장
    public void saveMessage(ChatMessage message) {

        opsChatLog.rightPush(message.getRoomId(), message);
    }

    // 메세지 리스트 불러오기
    public List<ChatMessage> findMessage(String roomId){
        messages = new ArrayList<>();
        for(int i = 0; i<opsChatLog.size(roomId); i++){
            messages.add((ChatMessage) opsChatLog.index(roomId, i));
        }
        return messages;
    }

    //현재 로그인 유저 추가
    public void saveLoginUser(String sessionId, String userId){
        loginUser.put(LOGIN_USERS,sessionId,userId);
    }

    //현재 로그인 유저 제거
    public void deleteLoginUser(String sessionId) {
        loginUser.delete(LOGIN_USERS,sessionId);
    }

    //현재 로그인 유저 리스트
    public List<User> loginList() {
        List<User> loginList = new ArrayList<>();

        for(String userId : loginUser.values(LOGIN_USERS)){
            loginList.add(userRepository.findById(Long.parseLong(userId)).orElseThrow(IllegalArgumentException::new));
        }

        return loginList;
    }

    /**
     * 채팅방 입장 : redis에 topic을 만들고 pub/sub 통신을 하기 위해 리스너를 설정한다.
     */
    public void enterChatRoom(ChatMessage message) {
        ChannelTopic topic = topics.get(message.getRoomId());
        if (topic == null)
            topic = new ChannelTopic(message.getRoomId());
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(message.getRoomId(), topic);
    }

    public void enterChatRoom(String userId) {
        ChannelTopic topic = topics.get(userId);
        if (topic == null)
            topic = new ChannelTopic(userId);
        redisMessageListener.addMessageListener(redisSubscriber, topic);
        topics.put(userId, topic);
    }

    public ChannelTopic getTopic(String roomId) {
        return topics.get(roomId);
    }



}
