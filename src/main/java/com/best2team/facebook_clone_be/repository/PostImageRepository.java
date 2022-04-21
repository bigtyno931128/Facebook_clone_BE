package com.best2team.facebook_clone_be.repository;

import com.best2team.facebook_clone_be.model.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

}
