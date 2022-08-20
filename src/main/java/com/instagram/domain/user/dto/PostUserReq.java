package com.instagram.domain.user.dto;


import com.instagram.domain.user.domain.AccountHiddenState;
import com.instagram.domain.user.domain.AccountType;
import com.instagram.domain.user.domain.PrivacyPolicyStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
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

    @ApiModelProperty(notes= "생일", example = "2021-06-28", required = true)
    @NotNull(message="생일 미입력")
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    @Past(message="생일 날짜 오류")   //현재보다 과거이면 통과
    private Date birthDay;  //생일

    @ApiModelProperty(notes= "개인정보 처리방침 동의여부", example = "AGREE", required = true)
    private PrivacyPolicyStatus privacyPolicyStatus;  //개인정보 처리방침 동의여부

    @ApiModelProperty(notes= "사용자 이름(닉네임)", example = "gridgetest12344", required = true)
    @Size(min=1, max=20, message="닉네임 형식 오류")
    private String nickName;  //사용자 이름(닉네임)


    //자동 입력
    private String email;  //이메일

}
