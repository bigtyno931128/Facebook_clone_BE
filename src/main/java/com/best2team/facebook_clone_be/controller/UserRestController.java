package com.best2team.facebook_clone_be.controller;

import com.best2team.facebook_clone_be.dto.LoginUserListDto;
import com.best2team.facebook_clone_be.dto.ProfileResponseDto;
import com.best2team.facebook_clone_be.dto.SignupRequestDto;
import com.best2team.facebook_clone_be.dto.UserResponseDto;

import com.best2team.facebook_clone_be.model.User;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.service.UserService;
import com.best2team.facebook_clone_be.websocket.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@RestController
public class UserRestController {
    private final UserService userService;
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public UserRestController(UserService userService, ChatRoomRepository chatRoomRepository){
        this.userService = userService;
        this.chatRoomRepository = chatRoomRepository;
    }

    // 회원가입
    @PostMapping("/user/signup")
    public String signup(@RequestBody SignupRequestDto signupRequestDto){
        System.out.println(signupRequestDto);

        return userService.signup(signupRequestDto);
    }

    @PostMapping("/api/user/islogin")
    public UserResponseDto isLogin(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return new UserResponseDto(userDetails);
    }

    @PostMapping("/api/user/image")
    public ProfileResponseDto registImage(@RequestParam("image") MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
        return userService.registImage(file, userDetails);
    }

    @GetMapping("/api/user/loginlist")
    public List<User> loginList(){
        return chatRoomRepository.loginList();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Object nullex(IllegalArgumentException e) {
        return e.getMessage();
    }
}
