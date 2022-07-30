package com.instagram.domain.user.service;


import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.PrivacyPolicyStatus;
import com.instagram.domain.user.domain.User;
import com.instagram.domain.user.dto.PostLoginReq;
import com.instagram.domain.user.dto.PostLoginRes;
import com.instagram.domain.user.dto.PostUserReq;
import com.instagram.global.error.BasicException;
import com.instagram.global.util.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.instagram.global.error.BasicResponseStatus.*;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final JwtService jwtservice;


    /* 1. 회원가입 API */
    public String createUser(PostUserReq postUserReq) throws BasicException {


        //전화번호 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.findByPhone(postUserReq.getPhone()) != null){
            throw new BasicException(RES_ERROR_EXISTS_PHONE);
        }

        //닉네임 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.findByNickName(postUserReq.getNickName()) != null){
            throw new BasicException(RES_ERROR_EXISTS_NICK_NAME);
        }



        try{

            //PostUserReq 객체의 데이터를 담을 User 엔티티 생성
            User userCreation = new User();
            BeanUtils.copyProperties(postUserReq,userCreation);

            //String형식인 birthDay 변수를 Date 형식으로 변환 (년,월,일 => - - )
            SimpleDateFormat formatter  = new SimpleDateFormat("yyyy년 MM월 dd일");
            Date birthDay = formatter.parse(postUserReq.getBirthDay());
            userCreation.setBirthDay(birthDay);

            //String형식인 privacyPolicyStatus 변수를 Enum 타입으로 변환
            PrivacyPolicyStatus privacyPolicyStatus = PrivacyPolicyStatus.valueOf(postUserReq.getPrivacyPolicyStatus());
            userCreation.setPrivacyPolicyStatus(privacyPolicyStatus);

            //Emial에 "null" 값 저장
            userCreation.setEmail("");

            //패스워드 암호화
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);  //BCryptPasswordEncoder 클래스 활용 (암호화 속도는 default가 10)
            userCreation.setPassword(encoder.encode(postUserReq.getPassword()));  //userCreation 객체에 암호화된 패스워드 삽입



            //user DB에 사용자 정보 등록
            userDao.save(userCreation);
            return "DB에 사용자 등록 성공";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_USER);  //"DB에 사용자 등록 실패""
        }
    }




    /* 2. 로그인 -  login() */
    public PostLoginRes login(PostLoginReq postLoginReq) throws BasicException {


        //가입된 유저인지 확인
        String bcryptPassword = null;
        User userLogin = userDao.findByPhone(postLoginReq.getPhone());
        if(userLogin != null){            //전화번호에 해당하는 사용자가 존재하면.
           bcryptPassword = userLogin.getPassword();
        }
        else{    //전화번호애 해당하는 사용자의 비밀번호가 없다면 오류메시지 출력
            throw new BasicException(RES_ERROR_JOIN_CHECK);                 //"가입되지 않은 유저"
        }


        //패스워드 일치여부 확인 (matches로 확인만 한다! 복호화 no)
        BCryptPasswordEncoder encoder2 = new BCryptPasswordEncoder();          //BCryptPasswordEncoder 클래스 활용
        if(!encoder2.matches(postLoginReq.getPassword(),bcryptPassword)){      //입력받은 password와 DB에서 불러온 (암호화된) password를 비교
            throw new BasicException(RES_ERROR_MATCH_FAIL_PASSWORD);    //"잘못된 비밀번호"
        }



        //jwt 발급 (accessToken)
        String accessToken = null;
        try {
            accessToken = jwtservice.createAccessToken(userLogin.getIdx());    //accessToken 발급 : 사용자 인가 절차 구현 (만료시간 3시간)
        }
        catch (Exception exceptio){
            throw new BasicException(ERROR_FAIL_ISSUE_ACCESS_TOKEN);  //"Access Token 발급 실패"
        }


        try {
            //postLoginRes 객체에 userIdx와 jwt를 담아 클라이언트에게 전송
            PostLoginRes postLoginRes = PostLoginRes.builder()
                    .userIdx(userLogin.getIdx())
                    .accessToken(accessToken)
                    .build();

            return postLoginRes;

        }catch (Exception exception) {
            throw new BasicException(RES_ERROR_LOGIN_USER);  //"로그인 실패"
        }




    }















}
