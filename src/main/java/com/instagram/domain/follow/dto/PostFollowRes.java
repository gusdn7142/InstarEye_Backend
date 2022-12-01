package com.instagram.domain.follow.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostFollowRes {

    @ApiModelProperty(notes= "팔로우 인덱스", example = "1", required = true)
    private Long followIdx;
}
