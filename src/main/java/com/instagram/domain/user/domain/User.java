package com.instagram.domain.user.domain;


import com.instagram.domain.model.DataStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;




@Getter
@Setter  //Setter를 써야할까??

@Entity
@DynamicInsert   //jap 메서드 동작시 null인 필드 제외
@DynamicUpdate   //jap 메서드 동작시 null인 필드 제외
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;  //인덱스

    @Column (nullable=false, columnDefinition="varchar(61)")
    private String name;  //이름

    @Column (nullable=false, columnDefinition="varchar(61)")
    private String nickName;  //사용자 이름

    @Column (nullable=false, columnDefinition="varchar(20)")
    private String phone;  //전화번호

    @Column (nullable=false,columnDefinition ="varchar(40)")
    private String email;  //이메일

    @Column (nullable=false, columnDefinition ="varchar(200)")
    private String password;  //패스워드

    @Column (nullable=false, columnDefinition ="varchar(200)")
    private String birthDay;  //생일

    @Column (nullable=true, columnDefinition ="varchar(200) default 'https://mblogthumb-phinf.pstatic.net/20150427_261/ninevincent_1430122791768m7oO1_JPEG/kakao_1.jpg?type=w2'")
    private String image;   //이미지 URL

    @Column (nullable=true, columnDefinition ="varchar(50)")
    private String webSite;  //웹사이트

    @Column (nullable=true, columnDefinition ="varchar(100)")
    private String Introduction;  //소개

    @Column (nullable=true, columnDefinition ="varchar(10) default 'OPEN'")
    //private String hiddenState;  //계정 공개 유무
    private AccountHiddenState accountHiddenState;  //계정 공개 유무

    @Column (nullable=false, columnDefinition ="varchar(10)")
    private PrivacyPolicyStatus privacyPolicyStatus;  //개인정보 처리방침 동의여부

    @Column (nullable=false, columnDefinition ="varchar(20) default 'LOCAL'")
    private AccountType accountType;  //계정 타입


    @Column (columnDefinition = "varchar(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private DataStatus status; //데이터 상태 (INACTIVE or ACTIVE)

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createAt;  //생성 시각

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateAt; //갱신 시각


}
