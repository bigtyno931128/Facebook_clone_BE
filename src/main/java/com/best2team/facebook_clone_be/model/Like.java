package com.best2team.facebook_clone_be.model;


import com.best2team.facebook_clone_be.dto.LikeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tbl_like")
public class Like {

    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long likeId;

    @Column
    private Long userId;

    @Column
    private Long postId;

    public Like(LikeDto likeDto){
        this.postId = likeDto.getPostId();
        this.userId = likeDto.getUserId();
    }

    public Like(Long postid, Long id) {
        this.postId=postid;
        this.userId=id;
    }
}
