package com.instagram.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder()
public class PostLoginRes {

    private Long userIdx;
    private String accessToken;

}
