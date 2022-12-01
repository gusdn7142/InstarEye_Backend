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
public class PostLoginReq {

    @ApiModelProperty(notes= "전화번호", example = "01012345671", required = true)
    @Pattern(regexp = "^[0-9]{11}" , message="전화번호 형식 오류")
    private String phone;

    @ApiModelProperty(notes= "비밀번호", example = "asdf1234", required = true)
    @Size(min=1, max=20, message="비밀번호 형식 오류")
    private String password;


}
