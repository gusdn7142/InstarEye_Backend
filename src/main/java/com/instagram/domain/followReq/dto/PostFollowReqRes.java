package com.instagram.domain.followReq.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostFollowReqRes {

    @ApiModelProperty(notes= "팔로우 요청 인덱스", example = "2", required = true)
    private Long followReqIdx;
}
