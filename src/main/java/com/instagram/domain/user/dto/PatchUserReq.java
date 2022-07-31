package com.instagram.domain.user.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
public class PatchUserReq {

    private Long userIdx;

    @Size(min=1, max=20, message="이름 형식 오류")
    private String name;  //이름

    @Size(min=1, max=20, message="닉네임 형식 오류")
    private String nickName;  //사용자 이름(닉네임)

    private String webSite;  //웹사이트

    private String introduction;  //소개

    @Pattern(regexp = "^(OPEN|PRIVATE)$", message="계정 공개상태 형식 오류")
    private String accountHiddenState; //계정 공개 유무


}
