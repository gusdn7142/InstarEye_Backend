package com.instagram.domain.user.dto;


import com.instagram.domain.user.domain.AccountHiddenState;
import com.instagram.domain.user.domain.AccountType;
import com.instagram.domain.user.domain.PrivacyPolicyStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {

    @ApiModelProperty(notes= "전화번호", example = "01012345671", required = true)
    @Pattern(regexp = "^[0-9]{11}" , message="전화번호 형식 오류")
    private String phone;  //전화번호

    @ApiModelProperty(notes= "이름", example = "그릿지", required = true)
    @Size(min=1, max=20, message="이름 형식 오류")
    private String name;  //이름

    @ApiModelProperty(notes= "비밀번호", example = "asdf1234", required = true)
    @Size(min=1, max=20, message="비밀번호 형식 오류")
    private String password;  //비밀번호

    @ApiModelProperty(notes= "생일", example = "2021년 6월 28일", required = true)
    @Pattern(regexp = "^[0-9]{4}년\\s[0-9]{1,2}월\\s[0-9]{1,2}일" , message="생일 형식 오류")
    private String birthDay;  //생일

    @ApiModelProperty(notes= "개인정보 처리방침 동의여부", example = "AGREE", required = true)
    @Pattern(regexp = "^(AGREE)$", message="개인정보 처리방침 미동의")
    private String privacyPolicyStatus;  //개인정보 처리방침 동의여부

    @ApiModelProperty(notes= "사용자 이름(닉네임)", example = "gridgetest12344", required = true)
    @Size(min=1, max=20, message="닉네임 형식 오류")
    private String nickName;  //사용자 이름(닉네임)


    //private String email;  //이메일

}
