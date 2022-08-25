package com.instagram.domain.user.service;


import com.instagram.domain.chat.domain.Chat;
import com.instagram.domain.comment.domain.Comment;
import com.instagram.domain.commentLike.domain.CommentLike;
import com.instagram.domain.follow.domain.Follow;
import com.instagram.domain.followReq.domain.FollowReq;
import com.instagram.domain.post.domain.Post;
import com.instagram.domain.postLike.domain.PostLike;
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
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
            //Email에 "null" 값 저장
            postUserReq.setEmail("");

            //패스워드 암호화
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);  //BCryptPasswordEncoder 클래스 활용 (암호화 속도는 default가 10)
            postUserReq.setPassword(encoder.encode(postUserReq.getPassword()));  //userCreation 객체에 암호화된 패스워드 삽입

            userDao.save(User.builder()
                    .phone(postUserReq.getPhone())
                    .name(postUserReq.getName())
                    .password(postUserReq.getPassword())
                    .birthDay(postUserReq.getBirthDay())
                    .privacyPolicyStatus(postUserReq.getPrivacyPolicyStatus())
                    .nickName(postUserReq.getNickName())
                    .email(postUserReq.getEmail())
                    .build());
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

        //개인정보 처리방침 동의 만료 확인
        if(userLogin.getPrivacyPolicyStatus() == PrivacyPolicyStatus.DISAGREE) {
            throw new BasicException(RES_ERROR_INVALID_PRIVACY_POLICY_STATUS);    //"개인정보 처리방침 재동의 필요"
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
    @Transactional(rollbackFor = {Exception.class})
    public void modifyUserInfo(PatchUserReq patchUserReq) throws BasicException {    //UserController.java에서 객체 값( id, nickName)을 받아와서...

        //닉네임 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.findByNickName(patchUserReq.getNickName()) != null){
            throw new BasicException(RES_ERROR_EXIST_NICK_NAME);
        }

        User user = userDao.findByIdx(patchUserReq.getUserIdx());
        try{
            //이름 변경
            if(patchUserReq.getName() != null){
                user.updateName(patchUserReq.getName());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경 오류"
        }


        try{
            //닉네임 값 변경
            if(patchUserReq.getNickName() != null){
                user.updateNickName(patchUserReq.getNickName());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NICKNAME);   //"닉네임 변경 오류"
        }



        try{
            //웹사이트 변경
            if(patchUserReq.getWebSite() != null){
                user.updateWebSite(patchUserReq.getWebSite());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_WEBSITE);   //"웹사이트 변경 오류"
        }


        try{
            //소개글 변경
            if(patchUserReq.getIntroduction() != null){
                user.updateIntroduction(patchUserReq.getIntroduction());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_INTRODUCTION);   //"소개글 변경 오류."
        }


        try{
            //계정공개 유무 변경
            if(patchUserReq.getAccountHiddenState() != null){
                user.updateHiddenState(patchUserReq.getAccountHiddenState());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_ACCOUNT_HIDDEN_STATE);   //"계정공개 유무 변경 오류."
        }



    }



    /* 5. 회원 탈퇴   */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteUser(Long userIdx) throws BasicException {

        //회원 탈퇴 여부 확인
        User user = userDao.findByIdx(userIdx);
        if(user == null){
            throw new BasicException(RES_ERROR_NOT_EXIST_USER);  //"존재하지 않는 사용자 계정"
        }

        try{
            //회원 탈퇴
            //1. 게시글 정보와 게시글 이미지 정보 삭제
            List<Post> postList = user.getPosts();
            postList.forEach(post -> {
                post.deletePost().getPostImages().forEach(postImageElement -> {
                    postImageElement.deletePostImage();
                });
            });
            //2. 게시글 좋아요 정보 삭제
            List<PostLike> postLikeList = user.getPostLikes();
            postLikeList.forEach(postLike -> {
                postLike.deletePostLike();
            });
            //3. 댓글 정보 삭제
            List<Comment> commentList = user.getComments();
            commentList.forEach(comment -> {
                comment.deleteComment();
            });
            //4. 댓글 좋아요 정보 삭제
            List<CommentLike> commentLikeList = user.getCommentLikes();
            commentLikeList.forEach(commentLike -> {
                commentLike.deleteCommentLike();
            });

            //5. 채팅 정보 삭제
            List<Chat> receiverChatList = user.getReceiverChats();
            List<Chat> senderChatList = user.getSenderChats();
            receiverChatList.forEach(receiverChat ->{
                receiverChat.deleteChat();
            });
            senderChatList.forEach(senderChat->{
                senderChat.deleteChat();;
            });
            //6. 팔로우 정보 삭제
            List<Follow> followeeFollowList= user.getFolloweeFollows();
            List<Follow> followerFollowList= user.getFollowerFollows();
            followeeFollowList.forEach(followeeFollow->{
                followeeFollow.deleteFollow();
            });
            followerFollowList.forEach(followerFollow->{
                followerFollow.deleteFollow();
            });
            //7. 팔로우 요청 정보 삭제
            List<FollowReq> reqFolloweeFollowList= user.getReqFolloweeFollowReqs();
            List<FollowReq> reqFollowerFollowList= user.getReqFollowerFollowReqs();
            reqFolloweeFollowList.forEach(reqFolloweeFollow->{
                reqFolloweeFollow.deleteFollowReq();
            });
            reqFollowerFollowList.forEach(reqFollowerFollow->{
                reqFollowerFollow.deleteFollowReq();
            });
            //8. 회원정보 삭제
            user.deleteUser();

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
            userDao.save(User.builder()
                    .phone(postKakaoUserReq.getPhone())
                    .name(postKakaoUserReq.getName())
                    .password(postKakaoUserReq.getPassword())
                    .birthDay(postKakaoUserReq.getBirthDay())
                    .privacyPolicyStatus(postKakaoUserReq.getPrivacyPolicyStatus())
                    .nickName(postKakaoUserReq.getNickName())
                    .email(postKakaoUserReq.getEmail())
                    .image(postKakaoUserReq.getImage())
                    .accountType(postKakaoUserReq.getAccountType())
                    .build());
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




    /* 개인정보 처리방침 재동의 API */
    @Transactional(rollbackFor = {Exception.class})
    public String reagreePrivacyPolicy(Long userIdx) throws BasicException {


        //가입된 유저인지 확인
        User user = userDao.findByIdx(userIdx);
        if(user == null){
            throw new BasicException(RES_ERROR_JOIN_CHECK);                 //"가입되지 않은 유저"
        }


        //개인정보 처리방침 동의 만료 확인
        if(user.getPrivacyPolicyStatus() != PrivacyPolicyStatus.DISAGREE) {
            throw new BasicException(RES_ERROR_VALID_PRIVACY_POLICY_STATUS);    //"개인정보 처리방침에 이미 동의한 상태"
        }



        try {
            //개인정보 처리방침 동의 DB에 반영
            userDao.reagreePrivacyPolicy(userIdx);

            //createdAt에 1년 추가 (스케줄러 동작에 맞추기 위함)
            userDao.addOneYeartoCreatedAt(userIdx);

            return "개인정보 처리방침 동의 완료! 로그인을 진행해 주세요.";

        }catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_FAIL_AGREE_PRIVACY_POLICY_STATUS);  //"개인정보 처리방침 재동의 실패"
        }




    }












}
