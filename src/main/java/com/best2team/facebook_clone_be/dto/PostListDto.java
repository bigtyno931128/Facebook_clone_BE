package com.best2team.facebook_clone_be.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostListDto {
    private Long postId;
    private String content;
    private int likeCnt;
    private int commentCnt;
    private LocalDateTime createAt;
    private String userImageUrl;
    private Long postImageId;
    private String postImageUrl;
    private String userName;
    private Long userId;
    private boolean like;

    public PostListDto(Long postId, String content, int likeCnt, int commentCnt, LocalDateTime createAt, String userImageUrl,
                       Long postImageId, String postImageUrl, String userName, Long userId, boolean like) {
        this.postId = postId;
        this.content = content;
        this.likeCnt = likeCnt;
        this.commentCnt = commentCnt;
        this.createAt = createAt;
        this.userImageUrl = userImageUrl;
        this.postImageId = postImageId;
        this.postImageUrl = postImageUrl;
        this.userName = userName;
        this.userId = userId;
        this.like = like;
    }
}
