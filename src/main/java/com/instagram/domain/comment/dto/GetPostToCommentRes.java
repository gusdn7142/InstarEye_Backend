package com.instagram.domain.comment.dto;

import io.swagger.annotations.ApiModelProperty;

public interface GetPostToCommentRes {


    @ApiModelProperty(notes= "게시글 인덱스", example = "1", required = true)
    Long getPostIdx();

    @ApiModelProperty(notes= "글쓴이 인덱스", example = "1", required = true)
    Long getWriterIdx();

    @ApiModelProperty(notes= "글쓴이 이미지", example = "a.png", required = true)
    String getWriterImage();

    @ApiModelProperty(notes= "글쓴이 닉네임", example = "gridgetest1", required = true)
    String getWriterNickName();

    @ApiModelProperty(notes= "게시글 내용", example = "안녕하세요. 그릿지1 수정 글 입니다.", required = true)
    String getPostContent();

    @ApiModelProperty(notes= "게시글 작성 시간", example = "1일", required = true)
    String getPostCreatedTime();



}
