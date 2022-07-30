package com.instagram.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
public class PostLoginReq {

    @Pattern(regexp = "^[0-9]{11}" , message="전화번호 형식 오류")
    private String phone;

    @Size(min=1, max=20, message="비밀번호 형식 오류")
    private String password;


}
