package com.instagram.domain.user.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder()
public class PostLoginRes {

    @ApiModelProperty(notes= "사용자 인덱스", example = "1", required = true)
    private Long userIdx;

    @ApiModelProperty(notes= "전화번호", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ", required = true)
    private String accessToken;

}
