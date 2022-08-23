package com.instagram.domain.user.dto;


import com.instagram.domain.user.domain.AccountType;
import com.instagram.domain.user.domain.PrivacyPolicyStatus;
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
    private String name;
    private String image;
    private String email;


    /* 클라이언트 입력 */
    @Pattern(regexp = "^[0-9]{11}" , message="전화번호 형식 오류")
    private String phone; //전화번호

    @Size(min=1, max=20, message="닉네임 형식 오류")
    private String nickName;  //닉네임

    private Date birthDay;  //생일

    private PrivacyPolicyStatus privacyPolicyStatus;  //개인정보 처리방침 동의여부


    /* 자동 입력 */
    private String password;  //패스워드 (카카오 ID 저장 예정)
    private AccountType accountType;  //계정 타입
}
