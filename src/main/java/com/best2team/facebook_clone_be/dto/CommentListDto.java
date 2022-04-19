package com.best2team.facebook_clone_be.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommentListDto {
    private List<CommentResponseDto> comments;
    private int totalPage;
    private int currentPage;

    public CommentListDto(Page<CommentResponseDto> page) {
        this.comments = page.getContent();
        this.totalPage = page.getTotalPages();
        this.currentPage = page.getNumber();

    }
}
