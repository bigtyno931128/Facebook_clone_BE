package com.best2team.facebook_clone_be.service;

import com.best2team.facebook_clone_be.dto.*;
import com.best2team.facebook_clone_be.model.Post;
import com.best2team.facebook_clone_be.model.PostImage;
import com.best2team.facebook_clone_be.model.User;
import com.best2team.facebook_clone_be.model.UserImage;
import com.best2team.facebook_clone_be.repository.*;
import com.best2team.facebook_clone_be.security.UserDetailsImpl;
import com.best2team.facebook_clone_be.utils.S3.S3Uploader;
import com.best2team.facebook_clone_be.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final S3Uploader s3Uploader;
    private final PostRepository postRepository;
    private final PostImageRepository imageRepository;
    private final Validator validator;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final PostImageRepository postImageRepository;
    private final UserImageRepository userImageRepository;

    @Transactional
    public PostListDto writePost(UserDetailsImpl userDetails, MultipartFile multipartFile, String content) throws IOException {

        validator.sameContent(content == null, "내용을 입력하세요");
        User user = userRepository.findById(userDetails.getUser().getUserId()).orElse(null);
        System.out.println(user.getUserName());

        PostImage postImage = new PostImage(s3Uploader.upload(multipartFile, "static"));
        Post post = new Post(content, user, postImageRepository.save(postImage));
        postRepository.save(post);

        Optional<UserImage> userImage = userImageRepository.findById(userDetails.getId());
        UserImage userImage1 = userImageRepository.findById(userDetails.getId()).orElse(null);

        String userImageUrl;
        String postImageUrl = "없음";

        if (post.getPostImage().getPostImageUrl() != null){
            postImageUrl = post.getPostImage().getPostImageUrl();
        }
        if (userImage.isPresent()){
            userImageUrl = userImage1.getImageUrl();
            System.out.println(userImage1.getImageUrl());
        }else{
            userImageUrl = "없음";
        }

        int like = likeRepository.countAllByPostId(post.getPostId());

        Long postImageId = post.getPostImage().getPostImageId();
        return new PostListDto(post.getPostId(),post.getContent(),like,commentRepository.countAllByPost(post),
                post.getCreatedAt(),userImageUrl,postImageUrl,post.getUser().getUserName(),post.getUser().getUserId(),
                likeRepository.findByPostIdAndUserId(post.getPostId(), userDetails.getUser().getUserId()).isPresent(),postImageId);
    }


    @Transactional
    public Page<PostListDto> showAllPost(int pageno, UserDetailsImpl userDetails) {
        List<Post> postList = postRepository.findAllByOrderByCreatedAtDesc();
        Pageable pageable = getPageable(pageno);
        List<PostListDto> postListDto = new ArrayList<>();
        forpostList(postList, postListDto, userDetails);

        int start = pageno * 7;
        int end = Math.min((start + 7), postList.size());

        return validator.overPages(postListDto, start, end, pageable, pageno);
    }

    private Pageable getPageable(int page) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(page, 7, sort);
    }

    @Transactional
    public void forpostList(List<Post> postList, List<PostListDto> postListDto, UserDetailsImpl userDetails) {
        for (Post post : postList) {
            int like = likeRepository.countAllByPostId(post.getPostId());

            String userImageUrl = "없음";
            String postImageUrl = "없음";

            if (post.getPostImage().getPostImageUrl() != null){
                postImageUrl = post.getPostImage().getPostImageUrl();
            }
            if (post.getUser().getUserImage() != null){
                userImageUrl = post.getUser().getUserImage().getImageUrl();
            };

            Long postImageId = post.getPostImage().getPostImageId();
            PostListDto postDto = new PostListDto(post.getPostId(),post.getContent(),like,commentRepository.countAllByPost(post),
                    post.getCreatedAt(),userImageUrl,postImageUrl,post.getUser().getUserName(),post.getUser().getUserId(),
                    likeRepository.findByPostIdAndUserId(post.getPostId(), userDetails.getUser().getUserId()).isPresent(),postImageId);

            postListDto.add(postDto);
        }
    }


    @Transactional
    public PostEditResponseDto editPost(Long postid, MultipartFile multipartFile, String content) throws IOException {
        Post post = postRepository.findById(postid).orElseThrow(IllegalArgumentException::new);
        PostImage postImage = new PostImage(s3Uploader.upload(multipartFile, "static"));
        postImageRepository.save(postImage);
        post.update(content,postImage);
        return new PostEditResponseDto(postid,content,postImage.getPostImageUrl());
    }

    @Transactional
    public void deletePost(Long postid) {
        try{
//            postImageRepository.deleteById(postRepository.findById(postid).orElseThrow(IllegalArgumentException::new).getPostImage().getPostImageId());
            likeRepository.deleteAllByPostId(postid);
//            commentRepository.deleteAllByPost(postRepository.findById(postid).orElseThrow(IllegalArgumentException::new));
            postRepository.deleteById(postid);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("게시글 삭제 오류!!!");
        }
    }

    //특정 유저로 게시글 조회 .
    public Page<PostListDto> getMyPage(int pageno, String username,UserDetailsImpl userDetails) {
        //받아온 username 으로 이 유저가 작성한 게시물 리스트 반환.
        List<Post> postList = postRepository.findAllByUserOrderByCreatedAtDesc(userRepository.findByUserName(username).orElseThrow(IllegalArgumentException::new));
        Pageable pageable = getPageable(pageno);
        List<PostListDto> postListDto = new ArrayList<>();
        forpostList(postList, postListDto, userDetails);

        int start = pageno * 7;
        int end = Math.min((start + 7), postList.size());

        return validator.overPages(postListDto, start, end, pageable, pageno);
    }
}

