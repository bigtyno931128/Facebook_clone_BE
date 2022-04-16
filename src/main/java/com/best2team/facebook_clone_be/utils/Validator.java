package com.best2team.facebook_clone_be.utils;


import com.best2team.facebook_clone_be.dto.CommentRequestDto;
import com.best2team.facebook_clone_be.dto.SignupRequestDto;
import com.best2team.facebook_clone_be.model.Comment;
import com.best2team.facebook_clone_be.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class Validator {
    private final UserRepository userRepository;


    @Autowired
    public Validator(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public void signupValidate(SignupRequestDto signupRequestDto) throws IllegalArgumentException {
        if (userRepository.findByUserEmail(signupRequestDto.getUserEmail()).isPresent()) {
            throw new IllegalArgumentException("중복된 아이디가 존재합니다.");
        }

        if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", signupRequestDto.getUserEmail())){
            throw new IllegalArgumentException("이메일 형식의 ID를 입력 해주세요.");
        }

        if(signupRequestDto.getPassword().length()<4){
            throw new IllegalArgumentException("비밀번호는 최소 4자 이상이여야 합니다.");
        }

        if(signupRequestDto.getPassword().contains(signupRequestDto.getUserEmail())){
            throw new IllegalArgumentException("ID가 포함되지 않은 비밀번호를 사용해주세요.");
        }
    }
//


    public static void emptyComment(CommentRequestDto requestDto) throws IllegalArgumentException {
        if(requestDto.getComment() == null) {
            throw new IllegalArgumentException("댓글을 입력하세요");
        }
    }

//    public void alreadyDelete(boolean favorite, String s) {
//        if(favorite){
//            throw new IllegalArgumentException(s);
//        }
//    }
//
//
    public void sameContent(boolean board, String s) {
        if(board){
            throw new IllegalArgumentException(s);
        }
    }

    public static void sameComment(CommentRequestDto requestDto) throws IllegalArgumentException {
        if(requestDto.getComment() == null){
            throw new IllegalArgumentException("수정된 내용이 없습니다.");
        }
    }



}