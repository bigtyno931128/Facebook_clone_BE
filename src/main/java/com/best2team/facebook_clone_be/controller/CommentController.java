package com.best2team.facebook_clone_be.controller;

import com.best2team.facebook_clone_be.dto.CommentListDto;
import com.best2team.facebook_clone_be.dto.CommentRequestDto;
import com.best2team.facebook_clone_be.dto.CommentResponseDto;
import com.best2team.facebook_clone_be.dto.MsgResponseDto;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor // final로 선언된 멤버 변수를 자동으로 생성합니다.
@RestController
public class CommentController {

    private final CommentService commentService;

    //댓글 등록
    @PostMapping("/api/comment")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto commentResponseDto = commentService.createComment(requestDto,userDetails);
        return ResponseEntity.ok(commentResponseDto);
    }

    //댓글 리스트 조회
    @GetMapping("/api/comment/{postid}/{pageno}")
    public CommentListDto getCommentList(@PathVariable Long postid,@PathVariable int pageno){
        CommentListDto commentListDto = new CommentListDto(commentService.getCommentList(postid, pageno-1));

        return commentListDto;
    }

    // 댓글 수정하기
    @PutMapping("/api/comment/{commentid}")
    public void updateComment(@PathVariable Long commentid, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
         commentService.updateComment(commentid,requestDto,userDetails);
    }

    // 댓글 삭제하기
    @DeleteMapping("/api/comment/{commentid}")
    public void deleteComment(@PathVariable Long commentid, @AuthenticationPrincipal UserDetailsImpl userDetails){
         commentService.deleteComment(commentid,userDetails);
    }
}