package com.best2team.facebook_clone_be.service;


import com.best2team.facebook_clone_be.dto.ImageDto;
import com.best2team.facebook_clone_be.dto.SignupRequestDto;
import com.best2team.facebook_clone_be.model.User;
import com.best2team.facebook_clone_be.model.UserImage;
import com.best2team.facebook_clone_be.repository.UserRepository;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.utils.S3.S3Uploader;
import com.best2team.facebook_clone_be.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final Validator validator;
    private final PasswordEncoder encoder;
    private final S3Uploader s3Uploader;

    @Autowired
    public UserService(UserRepository userRepository, Validator validator, PasswordEncoder encoder, S3Uploader s3Uploader){
        this.userRepository = userRepository;
        this.validator = validator;
        this.encoder = encoder;
        this.s3Uploader = s3Uploader;
    }

    // 회원가입
    public String signup(SignupRequestDto signupRequestDto) {
        String msg = "회원 가입이 완료되었습니다.";

        try {
            // 회원가입 검증
            validator.signupValidate(signupRequestDto);

        }catch (IllegalArgumentException e){
            msg = e.getMessage();
            return msg;
        }

        signupRequestDto.setPassword(encoder.encode(signupRequestDto.getPassword()));
        userRepository.save(new User(signupRequestDto));
        return msg;
    }
    //@Transactional
    public void registImage(MultipartFile file, UserDetailsImpl userDetails) throws IOException {
        Long userId = userDetails.getUser().getUserId();
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        UserImage userImage = new UserImage(s3Uploader.upload(file, "static"));
        user.update(userImage);
    }
}
