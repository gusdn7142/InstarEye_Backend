package com.instagram.domain.comment.dto;

public interface GetPostToCommentRes {


    Long getPostIdx();
    Long getWriterIdx();
    String getWriterImage();
    String getWriterNickName();
    String getPostContent();
    String getPostCreatedTime();



}
