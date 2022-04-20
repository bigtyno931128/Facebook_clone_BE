package com.best2team.facebook_clone_be.dto;

import com.best2team.facebook_clone_be.model.Comment;
import com.best2team.facebook_clone_be.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private Long postId;
    private String content;
    private String userName;
    private Long userId;
    private LocalDateTime createdAt;


    public CommentResponseDto(Long commentId, Long postId, String content, String userName, Long userId, LocalDateTime createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.content = content;
        this.userName = userName;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public CommentResponseDto(CommentRequestDto requestDto, User user, Comment comment) {
        this.commentId = comment.getCommentId();
        this.postId = requestDto.getPostId();
        this.content = requestDto.getComment();
        this.userName = user.getUserName();
        this.userId = user.getUserId();
        this.createdAt = comment.getCreatedAt();
    }
}
