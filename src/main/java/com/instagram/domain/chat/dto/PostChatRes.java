package com.instagram.domain.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PostChatRes {

    private Long receiverIdx;   //받는이 인덱스
    private String receiverNickName;  //받는이 닉네임
    private String receiverImage;   //받는이 이미지
    private String senderChatContent;  //보낸이 채팅 내용




}
