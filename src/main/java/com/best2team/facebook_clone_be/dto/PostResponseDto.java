package com.best2team.facebook_clone_be.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import java.util.List;

@Getter
@Setter
public class PostResponseDto {
    private List<PostListDto> postList;
    private int totalPage;

    public PostResponseDto(Page<PostListDto> postDtoList) {
        this.postList= postDtoList.getContent();
        this.totalPage= postDtoList.getTotalPages();

    }
}
