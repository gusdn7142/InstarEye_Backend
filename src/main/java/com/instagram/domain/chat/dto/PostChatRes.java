package com.instagram.domain.chat.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class PostChatRes {

    @ApiModelProperty(notes= "받는이 인덱스", example = "2", required = true)
    private Long receiverIdx;   //받는이 인덱스

    @ApiModelProperty(notes= "받는이 닉네임", example = "gridgetest1", required = true)
    private String receiverNickName;  //받는이 닉네임

    @ApiModelProperty(notes= "받는이 닉네임", example = "b.png", required = true)
    private String receiverImage;   //받는이 이미지

    @ApiModelProperty(notes= "보낸이 채팅내용", example = "이것은 채팅 메시지입니다.", required = true)
    private String senderChatContent;  //보낸이 채팅 내용




}
