package com.best2team.facebook_clone_be.service;

import com.best2team.facebook_clone_be.dto.LikeDto;
import com.best2team.facebook_clone_be.dto.LikeResponseDto;
import com.best2team.facebook_clone_be.model.Like;
import com.best2team.facebook_clone_be.repository.LikeRepository;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final Validator validator;

    //좋아요
    @Transactional
    public LikeResponseDto like(Long postId, UserDetailsImpl userDetails){

        //Like like = new Like(postId, userDetails.getId());
        Like like = likeRepository.findByPostIdAndUserId(postId, userDetails.getId()).orElse(null);
        LikeResponseDto likeResponseDto = new LikeResponseDto();
        try {
            if (like == null) {
                Like saveLike = new Like(postId, userDetails.getId());
                likeRepository.save(saveLike);
            } else {
                likeRepository.deleteById(like.getLikeId());
            }
            likeResponseDto.setMsg(true);
        }catch (Exception e ){
            likeResponseDto.setMsg(false);
        }
        return likeResponseDto;
    }


}
