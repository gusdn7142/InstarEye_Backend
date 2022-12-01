package com.instagram.domain.user.dto;


import com.instagram.domain.user.domain.AccountHiddenState;
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

    @ApiModelProperty(notes= "사용자 인덱스", example = "1", required = true)
    private Long userIdx;

    @ApiModelProperty(notes= "사용자 이름", example = "그릿지1", required = false)
    @Size(min=1, max=20, message="이름 형식 오류")
    private String name;  //이름

    @ApiModelProperty(notes= "사용자 닉네임", example = "gridgetest1", required = false)
    @Size(min=1, max=20, message="닉네임 형식 오류")
    private String nickName;  //사용자 이름(닉네임)

    @ApiModelProperty(notes= "웹사이트", example = "웹사이트 입력1", required = false)
    private String webSite;  //웹사이트

    @ApiModelProperty(notes= "사용자 소개", example = "소개 입력1", required = false)
    private String introduction;  //소개

    @ApiModelProperty(notes= "사용자 계정 공개 유무", example = "OPEN", required = false)
    private AccountHiddenState accountHiddenState; //계정 공개 유무


}
