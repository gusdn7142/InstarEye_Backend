package com.instagram.domain.postLike.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostPostLikeRes {
    @ApiModelProperty(notes= "게시글 좋아요 인덱스", example = "1", required = true)
    private Long postLikeIdx;
}
