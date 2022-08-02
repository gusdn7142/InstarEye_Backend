package com.instagram.domain.chat.dto;



public interface GetChatRes {

    Long getChatIdx(); //채팅 인덱스
    String getChatContent();  //채팅 내용
    String getCreatedTime(); //채팅 생성(수정) 시각

    Long getSenderIdx(); //보낸이 인덱스
    Long getReceiverIdx(); //받는이 인덱스
    String getReceiverImage(); //받는이 이미지

}
