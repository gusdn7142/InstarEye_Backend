package com.instagram.domain.commentLike.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCommentLikeRes {

    @ApiModelProperty(notes= "댓글 좋아요 인덱스", example = "1", required = true)
    private Long commentLikeIdx;

}
