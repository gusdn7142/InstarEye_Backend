package com.instagram.domain.post.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetPostRes {

    @ApiModelProperty(notes= "사용자 인덱스", example = "1", required = true)
    Long userIdx;  //사용자 인덱스

    @ApiModelProperty(notes= "사용자 닉네임", example = "gridgetest1", required = true)
    String userNickName;  //사용자 닉네임

    @ApiModelProperty(notes= "사용자 프로필 이미지", example = "a.jpg", required = true)
    String userimage;   //사용자 이미지

    @ApiModelProperty(notes= "게시글 인덱스", example = "14", required = true)
    Long postIdx;//게시글 인덱스

    @ApiModelProperty(notes= "게시글 내용", example = "안녕하세요. 그릿지3입니다.", required = true)
    String postContent; //게시글 내용

    @ApiModelProperty(notes= "게시글 이미지의 인덱스", example = "1", required = true)
    String postImageIdx;  //게시글 이미지의 인덱스

    @ApiModelProperty(notes= "게시글 이미지 파일", example = "image.png", required = true)
    String postimage;  //게시글 이미지

    @ApiModelProperty(notes= "게시글 이미지의 번호", example = "1", required = true)
    String postImageNumber;  //게시글 이미지의 번호

}