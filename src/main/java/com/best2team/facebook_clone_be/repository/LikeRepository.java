package com.best2team.facebook_clone_be.repository;

import com.best2team.facebook_clone_be.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like,Long> {
    int countAllByPostId(Long id);
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);

    void deleteAllByPostId(Long postid);


}
