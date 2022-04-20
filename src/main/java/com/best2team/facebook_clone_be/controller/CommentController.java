package com.best2team.facebook_clone_be.controller;

import com.best2team.facebook_clone_be.dto.*;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/api/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto,userDetails);

    }

    //댓글 리스트 조회
    @GetMapping("/api/comment/{postid}/{pageno}")
    public CommentListDto getCommentList(@PathVariable Long postid,@PathVariable int pageno){
        return new CommentListDto(commentService.getCommentList(postid, pageno-1));
    }


    // 댓글 수정하기
    @PutMapping("/api/comment/{commentid}")
    public ResponseEntity updateComment(@PathVariable Long commentid, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.updateComment(commentid,requestDto,userDetails);
        Message message = Message.builder()
                .message1("댓글 수정 성공!!")
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 댓글 삭제하기
    @DeleteMapping("/api/comment/{commentid}")
    public ResponseEntity deleteComment(@PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteComment(commentid,userDetails);
        Message message = Message.builder()
                .message1("댓글 삭제 성공!!")
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}