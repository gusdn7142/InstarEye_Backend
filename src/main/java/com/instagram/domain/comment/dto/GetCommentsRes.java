package com.instagram.domain.comment.dto;




public interface GetCommentsRes {

    Long getCommentIdx();
    Long getCommenterIdx();
    String getCommenterImage();
    String getCommenterNickName();
    String getCommentContent();
    String getCommentCreatedTime();

}
