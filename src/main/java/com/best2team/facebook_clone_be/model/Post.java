package com.best2team.facebook_clone_be.model;

import com.best2team.facebook_clone_be.utils.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tbl_post")
public class Post extends Timestamped {
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    private Long postId;

    @OneToOne
    @JoinColumn(name = "postImageId")
    private PostImage postImage;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private String content;

    public Post(String content, User user, PostImage postImage) {
        this.content=content;
        this.user = user;
        this.postImage = postImage;
    }

}
