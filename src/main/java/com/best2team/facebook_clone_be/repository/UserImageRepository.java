package com.best2team.facebook_clone_be.repository;

import com.best2team.facebook_clone_be.model.User;
import com.best2team.facebook_clone_be.model.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserImageRepository extends JpaRepository<UserImage,Long> {

}
