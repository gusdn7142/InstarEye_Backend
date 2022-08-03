package com.instagram.domain.user.service;


import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.AccountHiddenState;
import com.instagram.domain.user.domain.PrivacyPolicyStatus;
import com.instagram.domain.user.domain.User;
import com.instagram.domain.user.dto.*;
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
            throw new BasicException(RES_ERROR_EXIST_PHONE);
        }

        //닉네임 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.findByNickName(postUserReq.getNickName()) != null){
            throw new BasicException(RES_ERROR_EXIST_NICK_NAME);
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




    /* 2. 로그인  API */
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



    /* 3. 프로필 조회 API */
    public GetUserRes getUser(Long userIdx) throws BasicException {

        try {
        //프로필 조회
        GetUserRes getUserRes = userDao.getUser(userIdx);

            return getUserRes;

        }catch (Exception exception) {   //DB에서 받아온 객체가 null이면
            throw new BasicException(DATABASE_ERROR_GET_USER);
        }

    }


    /* 4. 프로필 수정 - 이름 */
    public void modifyUserInfo(PatchUserReq patchUserReq) throws BasicException {    //UserController.java에서 객체 값( id, nickName)을 받아와서...

        //닉네임 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.findByNickName(patchUserReq.getNickName()) != null){
            throw new BasicException(RES_ERROR_EXIST_NICK_NAME);
        }

        try{
            //이름 변경
            if(patchUserReq.getName() != null){
                userDao.modifyName(patchUserReq.getName(), patchUserReq.getUserIdx());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경 오류"
        }


        try{
            //닉네임 값 변경
            if(patchUserReq.getNickName() != null){
                userDao.modifyNickName(patchUserReq.getNickName(), patchUserReq.getUserIdx());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NICKNAME);   //"닉네임 변경 오류"
        }



        try{
            //웹사이트 변경
            if(patchUserReq.getWebSite() != null){
                userDao.modifyWebSite(patchUserReq.getWebSite(), patchUserReq.getUserIdx());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_WEBSITE);   //"웹사이트 변경 오류"
        }


        try{
            //소개글 변경
            if(patchUserReq.getIntroduction() != null){
                userDao.modifyIntroduction(patchUserReq.getIntroduction(), patchUserReq.getUserIdx());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_INTRODUCTION);   //"소개글 변경 오류."
        }


        try{
            //계정공개 유무 변경
            if(patchUserReq.getAccountHiddenState() != null){

                //String형식인 accountHiddenState 변수를 Enum 타입으로 변환
                AccountHiddenState accountHiddenState = AccountHiddenState.valueOf(patchUserReq.getAccountHiddenState());
                userDao.modifyAccountHiddenState(accountHiddenState, patchUserReq.getUserIdx());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_ACCOUNT_HIDDEN_STATE);   //"계정공개 유무 변경 오류."
        }



    }



    /* 5. 회원 탈퇴   */
    public void deleteUser(Long userIdx) throws BasicException {

        //회원 탈퇴 여부 확인
        if(userDao.findByIdx(userIdx) == null){
            throw new BasicException(RES_ERROR_NOT_EXIST_USER);  //"존재하지 않는 사용자 계정"
        }

        try{
            //회원 탈퇴
            userDao.deleteUser(userIdx);
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_DELETE_USER);   //'회원 탈퇴 실패'
        }

    }



    /* 카카오 회원가입  */
    public String createKakaoUser(PostKakaoUserReq postKakaoUserReq) throws BasicException {


        //카카오 유저로 가입이 되어 있는지 확인 (카카오 ID 활용)
        String userPassword = postKakaoUserReq.getPassword().toString();
        User userkakaoLogin = userDao.findByPassword(userPassword);
        if(userkakaoLogin != null){
            throw new BasicException(RES_ERROR_EXIST_KAKAO_USER);  //이미 존재하는 카카오 계정
        }

        //전화번호 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.findByPhone(postKakaoUserReq.getPhone()) != null){
            throw new BasicException(RES_ERROR_EXIST_PHONE);
        }

        //닉네임 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.findByNickName(postKakaoUserReq.getNickName()) != null){
            throw new BasicException(RES_ERROR_EXIST_NICK_NAME);
        }


        //카카오 유저 계정 생성
        try{

            //PostUserReq 객체의 데이터를 담을 User 엔티티 생성
            User userCreation = new User();

            //String형식인 privacyPolicyStatus 변수를 Enum 타입으로 변환
            PrivacyPolicyStatus privacyPolicyStatus = PrivacyPolicyStatus.valueOf(postKakaoUserReq.getPrivacyPolicyStatus());
            userCreation.setPrivacyPolicyStatus(privacyPolicyStatus);

            BeanUtils.copyProperties(postKakaoUserReq,userCreation);

            userDao.save(userCreation);
            return "카카오 회원가입 성공";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_USER);  //"DB에 사용자 등록 실패""
        }


    }



    /* 카카오 로그인  */
    public PostLoginRes kakaoLogIn(String kakaoId) throws BasicException {


        //카카오 유저로 가입이 되어 있는지 확인
        User userkakaoLogin = userDao.findByPassword(kakaoId);
        if(userkakaoLogin == null){
            throw new BasicException(RES_ERROR_NOT_EXIST_KAKAO_USER);   //존재하지 않는 카카오 계정 (카카오 회원가입 필요)
        }


        //jwt 발급 (accessToken)
        String accessToken = null;
        try {
            accessToken = jwtservice.createAccessToken(userkakaoLogin.getIdx());    //accessToken 발급 : 사용자 인가 절차 구현 (만료시간 3시간)
        }
        catch (Exception exceptio){
            throw new BasicException(ERROR_FAIL_ISSUE_ACCESS_TOKEN);  //"Access Token 발급 실패"
        }


        try {
            //postLoginRes 객체에 userIdx와 jwt를 담아 클라이언트에게 전송
            PostLoginRes postLoginRes = PostLoginRes.builder()
                    .userIdx(userkakaoLogin.getIdx())
                    .accessToken(accessToken)
                    .build();

            return postLoginRes;
        }catch (Exception exception) {
            throw new BasicException(RES_ERROR_LOGIN_USER);  //"로그인 실패"
        }


    }






}
