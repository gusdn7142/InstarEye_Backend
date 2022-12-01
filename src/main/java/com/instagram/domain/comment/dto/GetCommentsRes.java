package com.instagram.domain.comment.dto;


import io.swagger.annotations.ApiModelProperty;

public interface GetCommentsRes {

    @ApiModelProperty(notes= "댓글 인덱스", example = "11", required = true)
    Long getCommentIdx();

    @ApiModelProperty(notes= "댓글 작성자 인덱스", example = "1", required = true)
    Long getCommenterIdx();

    @ApiModelProperty(notes= "댓글 작성자 이미지", example = "a.png", required = true)
    String getCommenterImage();

    @ApiModelProperty(notes= "댓글 작성자 닉네임", example = "gridgetest2", required = true)
    String getCommenterNickName();

    @ApiModelProperty(notes= "댓글 내용", example = "넵, 저도 한번 써봐야 겠네요", required = true)
    String getCommentContent();

    @ApiModelProperty(notes= "댓글 작성 시간", example = "11일", required = true)
    String getCommentCreatedTime();

    @ApiModelProperty(notes= "댓글 좋아요 수", example = "0개", required = true)
    String getCommentLikeCount();

    @ApiModelProperty(notes= "좋아요 클릭 상태", example = "INACTIVE", required = true)
    String getLikeClickStatus();

}
