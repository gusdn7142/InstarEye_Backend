package com.instagram.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ElementCollection;
import java.time.LocalDateTime;
import java.util.List;


public interface GetPostsRes {


    Long getWriter();  //사용자 인덱스
    String getWriterNickName();  //사용자 닉네임
    String getImage();   //사용자 이미지

    Long getPostIdx();//게시글 인덱스
    String getPostContent(); //게시글 내용
    String getPostCreatedDate(); //게시글 작성일(수정일)

    String getPostImageIdx();  //게시글 이미지의 인덱스
    String getPostimage();  //게시글 이미지
    String getPostImageNumber();  //게시글 이미지의 번호




}
