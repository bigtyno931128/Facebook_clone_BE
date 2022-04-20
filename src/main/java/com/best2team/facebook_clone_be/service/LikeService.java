package com.best2team.facebook_clone_be.service;

import com.best2team.facebook_clone_be.dto.LikeDto;
import com.best2team.facebook_clone_be.model.Like;
import com.best2team.facebook_clone_be.repository.LikeRepository;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    //좋아요
    public String like(Long postid, UserDetailsImpl userDetails){
        Like like = likeRepository.findByPostIdAndUserId(postid,userDetails.getId()).orElse(null);
            if (like == null) {
                Like savelike = new Like(postid, userDetails.getId());
                likeRepository.save(savelike);
                return "true";
            } else {
                likeRepository.deleteById(like.getLikeId());
                return "false";
            }

    }

}
