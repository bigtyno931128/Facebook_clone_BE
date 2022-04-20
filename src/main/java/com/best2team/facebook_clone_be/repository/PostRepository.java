package com.best2team.facebook_clone_be.repository;
import com.best2team.facebook_clone_be.model.Post;
import com.best2team.facebook_clone_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByUserOrderByCreatedAt(User user);
    void deleteAllByPostId(Long postid);


    List<Post> findAllByOrderByCreatedAtDesc();
}
