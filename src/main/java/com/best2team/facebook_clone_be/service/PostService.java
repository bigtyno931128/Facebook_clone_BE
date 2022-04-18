package com.best2team.facebook_clone_be.service;

import com.best2team.facebook_clone_be.dto.*;
import com.best2team.facebook_clone_be.model.Post;
import com.best2team.facebook_clone_be.model.PostImage;
import com.best2team.facebook_clone_be.model.User;
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

    @Transactional
    public MsgResponseDto writePost(UserDetailsImpl userDetails, MultipartFile multipartFile, String content) throws IOException {
        try {
            validator.sameContent(content == null, "내용을 입력하세요");
            User user = userRepository.findById(userDetails.getUser().getUserId()).orElse(null);

            PostImage postImage = new PostImage(s3Uploader.upload(multipartFile, "static"));
            System.out.println(postImage);
            //System.out.println(postImage.getPostImageUrl());
            Post post = new Post(content, user, imageRepository.save(postImage));
            postRepository.save(post);
            String msg = "게시글 작성이 성공되었습니다.";
            return new MsgResponseDto(msg);

        } catch (Exception e) {
            String msg = "게시글 작성 XXX";
            return new MsgResponseDto(msg);
        }
    }


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

    private void forpostList(List<Post> postList, List<PostListDto> postListDto, UserDetailsImpl userDetails) {
        for (Post post : postList) {
            int like = likeRepository.countAllByPostId(post.getPostId());

            String userImageUrl = "없음";
            String postImageUrl = "없음";

            if (post.getPostImage().getPostImageUrl() != null){
                userImageUrl = post.getPostImage().getPostImageUrl();
            }
            if (post.getUser().getUserImage() != null){
                userImageUrl = post.getUser().getUserImage().getImageUrl();
            };
            Long postImageId = post.getPostImage().getPostImageId();

            PostListDto postDto = new PostListDto(post.getPostId(),post.getContent(),like,commentRepository.countAllByPost(post),post.getCreatedAt(),userImageUrl,postImageUrl,post.getUser().getUserName(),post.getUser().getUserId(),
                    likeRepository.findByPostIdAndUserId(post.getPostId(), userDetails.getUser().getUserId()).isPresent(),postImageId);


//            PostListDto postDto = new PostListDto(post.getPostId(), post.getContent(), like, commentRepository.countAllByPost(post),
//                    post.getCreatedAt(), userImageUrl, imageRepository.findById(post.getPostId()).orElseThrow(IllegalArgumentException::new).getPostImageId(),
//                    postImageUrl, post.getUser().getUserName(), post.getUser().getUserId(),
//                    likeRepository.findByPostIdAndUserId(post.getPostId(), userDetails.getUser().getUserId()).isPresent());

            postListDto.add(postDto);
        }
    }


    @Transactional
    public PostEditResponseDto editPost(Long postid, MultipartFile multipartFile, String content) throws IOException {

        Post post = postRepository.findById(postid).orElseThrow(IllegalArgumentException::new);

        //postImageRepository.deleteById(post.getPostImage().getPostImageId());
        PostImage postImage = new PostImage(s3Uploader.upload(multipartFile, "static"));
        postImageRepository.save(postImage);
        post.update(content,postImage);

        return new PostEditResponseDto(postid,content,postImage.getPostImageUrl());
    }

    @Transactional
    public MsgResponseDto deletePost(Long postid) {
        postImageRepository.deleteById(postRepository.findById(postid).orElseThrow(IllegalArgumentException::new).getPostImage().getPostImageId());
        likeRepository.deleteAllByPostId(postid);
//        s3Uploader.deleteFile(postRepository.findById(postid).orElseThrow(IllegalArgumentException::new).getPostImage().getPostImageId());
        commentRepository.deleteAllByPost(postRepository.findById(postid).orElseThrow(IllegalArgumentException::new));
        postRepository.deleteById(postid);
        return new MsgResponseDto("게시글 삭제가 완료되었습니다");
    }

    //특정 유저로 게시글 조회 .
    public Page<PostListDto> getMyPage(int i, String username,UserDetailsImpl userDetails) {
        User user =userRepository.findByUserName(username).orElseThrow(IllegalArgumentException::new);
        //받아온 username 으로 이 유저가 작성한 게시물 리스트 반환.
        List<Post> postList = postRepository.findAllByUserOrderByCreatedAt(user);
        Pageable pageable = getPageable(i);
        List<PostListDto> postListDto = new ArrayList<>();
        forpostList(postList, postListDto, userDetails);

        int start = i * 7;
        int end = Math.min((start + 7), postList.size());

        return validator.overPages(postListDto, start, end, pageable, i);
    }
}

