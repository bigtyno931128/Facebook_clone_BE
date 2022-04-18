package com.best2team.facebook_clone_be.controller;

import com.best2team.facebook_clone_be.dto.LikeDto;
import com.best2team.facebook_clone_be.dto.LikeResponseDto;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class LikeController {
    private final LikeService likeService;

    @ExceptionHandler(IllegalArgumentException.class)
    public Object nullex(Exception e) {
        return e.getMessage();
    }

    @PostMapping("/api/post/like/{postId}")
    private LikeResponseDto pressFavorite(@PathVariable Long postId , @AuthenticationPrincipal UserDetailsImpl userDetails){

        return likeService.like(postId,userDetails);
    }

//    @DeleteMapping("/api/board/favoriteboard/{postid}/{userid}")
//    private String unpressFavorite(@PathVariable Long postid, @PathVariable Long userid){
//        return likeService.unLike(postid, userid);
//    }
}
