package com.best2team.facebook_clone_be.repository;

import com.best2team.facebook_clone_be.model.Comment;
import com.best2team.facebook_clone_be.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPost(Post post);
    int countAllByPost(Post post);


    void deleteAllByPost(Post orElseThrow);

    List<Comment> findAllByPostOrderByCreatedAtDesc(Post orElseThrow);


    //Page<Comment> findByUserIdOrderByCommentIdDesc(Long userId, Pageable pageable);
}
