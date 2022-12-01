package com.instagram.domain.chat.dto;


import io.swagger.annotations.ApiModelProperty;

public interface GetChatRes {

    @ApiModelProperty(notes= "채팅 인덱스", example = "1", required = true)
    Long getChatIdx(); //채팅 인덱스

    @ApiModelProperty(notes= "채팅 내용", example = "이것은 채팅 메시지입니다.", required = true)
    String getChatContent();  //채팅 내용

    @ApiModelProperty(notes= "채팅 생성(수정) 시각", example = "오전 12:06", required = true)
    String getCreatedTime(); //채팅 생성(수정) 시각

    @ApiModelProperty(notes= "보낸이 인덱스", example = "1", required = true)
    Long getSenderIdx(); //보낸이 인덱스

    @ApiModelProperty(notes= "받는이 인덱스", example = "1", required = true)
    Long getReceiverIdx(); //받는이 인덱스

    @ApiModelProperty(notes= "받는이 이미지", example = "b.png", required = true)
    String getReceiverImage(); //받는이 이미지

}
