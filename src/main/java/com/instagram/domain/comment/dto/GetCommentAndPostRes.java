package com.instagram.domain.comment.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCommentAndPostRes {

    @ApiModelProperty(notes= "게시글에 대한 댓글 정보", required = true)
    private GetPostToCommentRes getPostToCommentRes;

    @ApiModelProperty(notes= "댓글 정보", position = 2, required = true)
    private List<GetCommentsRes> getCommentsRes;

}
