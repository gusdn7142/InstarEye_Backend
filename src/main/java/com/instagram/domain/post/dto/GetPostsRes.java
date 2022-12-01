package com.instagram.domain.post.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import java.time.LocalDateTime;
import java.util.List;


public interface GetPostsRes {

    @ApiModelProperty(notes= "사용자 인덱스", example = "1", required = true)
    Long getWriterIdx();  //사용자 인덱스
    @ApiModelProperty(notes= "사용자 닉네임", example = "gridgetest1", required = true)
    String getWriterNickName();  //사용자 닉네임
    @ApiModelProperty(notes= "사용자 프로필 이미지", example = "a.jpg", required = true)
    String getWriterImage();   //사용자 이미지

    @ApiModelProperty(notes= "게시글 인덱스", example = "14", required = true)
    Long getPostIdx();//게시글 인덱스
    @ApiModelProperty(notes= "게시글 내용", example = "안녕하세요. 그릿지3입니다.", required = true)
    String getPostContent(); //게시글 내용
    @ApiModelProperty(notes= "게시글 작성일", example = "10일 전", required = true)
    String getPostCreatedDate(); //게시글 작성일(수정일)

    @ApiModelProperty(notes= "게시글 이미지의 인덱스", example = "1", required = true)
    String getPostImageIdx();  //게시글 이미지의 인덱스
    @ApiModelProperty(notes= "게시글 이미지 파일", example = "image.png", required = true)
    String getPostimage();  //게시글 이미지
    @ApiModelProperty(notes= "게시글 이미지의 번호", example = "1", required = true)
    String getPostImageNumber();  //게시글 이미지의 번호

    @ApiModelProperty(notes= "게시글 좋아요 수", example = "1", required = true)
    String getPostLikeCount();  //게시글 좋아요 수
    @ApiModelProperty(notes= "댓글 수", example = "2", required = true)
    String getCommentCount();  //댓글 수
    @ApiModelProperty(notes= "좋아요 클릭 여부", example = "INACTIVE", required = true)
    String getLikeClickStatus();  //좋아요 클릭 여부


}
