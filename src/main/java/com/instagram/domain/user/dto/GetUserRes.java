package com.instagram.domain.user.dto;




public interface GetUserRes {

    Long getUserIdx();
    String getUserName();
    String getUserImage();
    String getUserNickName();
    String getUserWebSite();
    String getUserIntroduction();

    Long getPostCount();
    Long getFollowerCount();
    Long getFolloweeCount();
    String getPostIdx();
    String getPostImage();




}
