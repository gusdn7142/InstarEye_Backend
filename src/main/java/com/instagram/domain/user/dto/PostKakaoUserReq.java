package com.instagram.domain.user.dto;


import com.instagram.domain.user.domain.AccountType;
import com.instagram.domain.user.domain.PrivacyPolicyStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class PostKakaoUserReq {

    /* 카카오 서버에서 조회하여 입력 */
    @ApiModelProperty(notes= "사용자 이름", example = "데비스", required = true)
    private String name;
    @ApiModelProperty(notes= "사용자 이미지", example = "a.png", required = true)
    private String image;
    @ApiModelProperty(notes= "사용자 이메일", example = "asds@gmail.com", required = true)
    private String email;


    /* 클라이언트 입력 */
    @ApiModelProperty(notes= "전화번호", example = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ", required = true)
    @Pattern(regexp = "^[0-9]{11}" , message="전화번호 형식 오류")
    private String phone; //전화번호

    @ApiModelProperty(notes= "사용자 닉네임", example = "gridgetest4.eyJ", required = true)
    @Size(min=1, max=20, message="닉네임 형식 오류")
    private String nickName;  //닉네임

    @ApiModelProperty(notes= "사용자 생일", example = "2021-01-10", required = true)
    private Date birthDay;  //생일

    @ApiModelProperty(notes= "개인정보 처리방침 동의여부", example = "AGREE", required = true)
    private PrivacyPolicyStatus privacyPolicyStatus;  //개인정보 처리방침 동의여부


    /* 자동 입력 */
    @ApiModelProperty(notes= "사용자 비밀번호", example = "2373307928", required = true)
    private String password;  //패스워드 (카카오 ID 저장 예정)

    @ApiModelProperty(notes= "사용자 계정 타입", example = "KAKAO", required = true)
    private AccountType accountType;  //계정 타입
}
