package com.instagram.domain.user.dto;


import com.instagram.domain.user.domain.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


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

    @Pattern(regexp = "^[0-9]{4}년\\s[0-9]{1,2}월\\s[0-9]{1,2}일" , message="생일 형식 오류")
    private String birthDay;  //생일

    @Pattern(regexp = "^(AGREE)$", message="개인정보 처리방침 미동의")
    private String privacyPolicyStatus;  //개인정보 처리방침 동의여부



    /* 자동 입력 */
    private String password;  //패스워드 (카카오 ID 저장 예정)
    private AccountType accountType;  //계정 타입




}
