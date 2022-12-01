package com.instagram.domain.user.dto;


import io.swagger.annotations.ApiModelProperty;

public interface GetUserRes {

    @ApiModelProperty(notes= "사용자 인덱스", example = "1", required = true)
    Long getUserIdx();

    @ApiModelProperty(notes= "사용자 이름", example = "그릿지1", required = true)
    String getUserName();

    @ApiModelProperty(notes= "사용자 프로필 이미지", example = "a.jpg", required = true)
    String getUserImage();

    @ApiModelProperty(notes= "사용자 닉네임", example = "gridgetest1", required = true)
    String getUserNickName();

    @ApiModelProperty(notes= "웹사이트", example = "웹사이트 입력1", required = true)
    String getUserWebSite();

    @ApiModelProperty(notes= "사용자 소개", example = "소개 입력1", required = true)
    String getUserIntroduction();

    @ApiModelProperty(notes= "게시글 숫자", example = "14", required = true)
    Long getPostCount();

    @ApiModelProperty(notes= "팔로워 수", example = "0", required = true)
    Long getFollowerCount();

    @ApiModelProperty(notes= "팔로위 수", example = "0", required = true)
    Long getFolloweeCount();

    @ApiModelProperty(notes= "게시글 인덱스", example = "1", required = true)
    String getPostIdx();

    @ApiModelProperty(notes= "게시글 이미지", example = "image.png", required = true)
    String getPostImage();


}
