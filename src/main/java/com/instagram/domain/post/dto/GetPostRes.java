package com.instagram.domain.post.dto;


public interface GetPostRes {


    Long getUserIdx();  //사용자 인덱스
    String getUserNickName();  //사용자 닉네임
    String getUserImage();   //사용자 이미지

    Long getPostIdx();//게시글 인덱스
    String getPostContent(); //게시글 내용

    String getPostImageIdx();  //게시글 이미지의 인덱스
    String getPostimage();  //게시글 이미지
    String getPostImageNumber();  //게시글 이미지의 번호




}
