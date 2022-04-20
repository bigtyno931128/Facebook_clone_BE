package com.best2team.facebook_clone_be.controller;

import com.best2team.facebook_clone_be.dto.*;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/api/post")
    public PostListDto writePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("image") MultipartFile multipartFile, @RequestParam("content") String content) throws IOException {
        return postService.writePost(userDetails, multipartFile, content);
    }

    @GetMapping("/api/post/{pageno}")
    public PostResponseDto showAllPost(@PathVariable("pageno") int pageno, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new PostResponseDto(postService.showAllPost(pageno-1, userDetails));
    }

    @PutMapping("/api/post/{postid}")
    public PostEditResponseDto editPost(@PathVariable("postid") Long postid,@RequestParam("image") MultipartFile multipartFile, @RequestParam("content") String content) throws IOException {
        return postService.editPost(postid,multipartFile,content);
    }

    @DeleteMapping("/api/post/{postid}")
    public ResponseEntity deletePost(@PathVariable("postid") Long postid) {
        postService.deletePost(postid);
        Message message = Message.builder()
                .message1("게시글 삭제 성공!!")
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/api/post/{username}/{pageno}")
    public PostResponseDto getMyPage(@PathVariable String username ,@PathVariable int pageno,@AuthenticationPrincipal UserDetailsImpl userDetails) throws UnsupportedEncodingException {
        username = URLDecoder.decode(username, "UTF-8");
        return new PostResponseDto(postService.getMyPage(pageno-1,username,userDetails));
    }


    //특정 유저 게시글 조회
    @GetMapping("/api/post/{username}/{pageno}")
    public PostResponseDto getMyPage(@PathVariable String username ,@PathVariable int pageno,@AuthenticationPrincipal UserDetailsImpl userDetails) throws UnsupportedEncodingException {
        username = URLDecoder.decode(username, "UTF-8");
        System.out.println(username);
        return new PostResponseDto(postService.getMyPage(pageno-1,username,userDetails));
    }

}
