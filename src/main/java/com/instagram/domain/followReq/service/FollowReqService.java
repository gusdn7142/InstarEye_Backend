package com.instagram.domain.followReq.service;


import com.instagram.domain.follow.dao.FollowDao;
import com.instagram.domain.follow.domain.Follow;
import com.instagram.domain.followReq.dao.FollowReqDao;
import com.instagram.domain.followReq.domain.FollowReq;
import com.instagram.domain.followReq.dto.PostFollowReqRes;
import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.AccountHiddenState;
import com.instagram.domain.user.domain.User;
import com.instagram.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.instagram.global.error.BasicResponseStatus.*;

@Service
@RequiredArgsConstructor
public class FollowReqService {

    private final FollowReqDao followReqDao;
    private final UserDao userDao;
    private final FollowDao followDao;


    /* 22. 팔로우 요청  */
    public PostFollowReqRes createfollowReq(Long followerReqIdx, Long followeeReqIdx) throws BasicException {


        User reqFollower = userDao.findByIdx(followerReqIdx);
        User reqFollowee = userDao.findByIdx(followeeReqIdx);


        //팔로위 계정 삭제 여부 조회
        if(reqFollowee == null){
            throw new BasicException(RES_ERROR_NOT_EXIST_REQFOLLOWEE);    //"존재하지 않는 팔로위 계정"
        }

        //팔로위 계정이 비공개 상태인지 확인
        if(reqFollowee.getAccountHiddenState() != AccountHiddenState.PRIVATE){
            throw new BasicException(RES_ERROR_FOLLOWREQS_OPEN_ACCOUNT);    //"공개된 계정이므로 팔로우 API 이용 필요"
        }

        //팔로우 요청 중복 조회
        FollowReq followReqCheck = followReqDao.findByReqFollowerAndReqFollowee(reqFollower, reqFollowee);
        if(followReqCheck != null){
            throw new BasicException(RES_ERROR_FOLLOWREQS_EXIST_FOLLOW_REQ);    //"팔로우 요청 중복 오류"
        }

        //팔로우 중복 조회
        Follow followCheck = followDao.findByFollowerAndFollowee(reqFollower, reqFollowee);
        if(followCheck != null){
            throw new BasicException(RES_ERROR_FOLLOWS_EXIST_FOLLOW);    //"팔로우 중복 오류"
        }



        //DB에 팔로우 요청 정보 등록
        try{

            //folloReq DB에  팔로우 요청 정보 저장
            FollowReq followReqCreation = new FollowReq();
            followReqCreation.setReqFollowee(reqFollowee);
            followReqCreation.setReqFollower(reqFollower);

            followReqDao.save(followReqCreation);

            //followReqIdx 반환
            FollowReq followReq = followReqDao.findByReqFollowerAndReqFollowee(reqFollower, reqFollowee);
            PostFollowReqRes postFollowReqRes = new PostFollowReqRes(followReq.getIdx());

            return postFollowReqRes;

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_FOLLOWREQS);  //"DB에 팔로우 요청 등록 실패"
        }




    }


    /* 23. 팔로우 요청 취소 -  */
    @Transactional(rollbackFor = {Exception.class})
    public void deletefollowReq(Long followReqIdx, Long userIdx) throws BasicException {

        // 팔로우 요청정보 삭제 여부 조회
        FollowReq followReqDelete = followReqDao.findByIdx(followReqIdx);
        if(followReqDelete == null){
            throw new BasicException(RES_ERROR_FOLLOWREQS_DELETE_FOLLOWREQ);    //"이미 취소된 팔로우 요청"
        }

        //팔로우 요청 등록자 체크
        User reqfollower = userDao.findByIdx(userIdx);
        if(followReqDao.checkReqFollower(followReqIdx, reqfollower) == null){
            throw new BasicException(RES_ERROR_FOLLOWREQS_NOT_SAME_REQFOLLOWER);    //팔로우 요청 등록자 불일치 오류
        }

        try{
            //팔로우 요청 정보 삭제
            followReqDelete.deleteFollowReq();
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_FAIL_DELETE_FOLLOWREQS);   //'DB에서 팔로우 요청 취소 실패'
        }
    }



    /* 27. 팔로우 요청 거절 -  */
    @Transactional(rollbackFor = {Exception.class})
    public void refusefollowReq(Long followReqIdx, Long userIdx) throws BasicException {

        // 팔로우 요청정보 삭제 여부 조회 (유저가 계속 클릭시..)
        FollowReq followReqDelete = followReqDao.findByIdx(followReqIdx);
        if(followReqDelete == null){
            throw new BasicException(RES_ERROR_FOLLOWREQS_DELETE_FOLLOWREQ);    //"이미 취소된 팔로우 요청"
        }
        //팔로우 요청 받은 내역이 있는지 체크
        User reqfollowee = userDao.findByIdx(userIdx);
        if(followReqDao.checkReqFollowee(followReqIdx, reqfollowee) == null){
            throw new BasicException(RES_ERROR_FOLLOWREQS_NOT_SAME_REQFOLLOWEE);    //팔로우 요청 받은 내역 없음
        }

        try{
            //팔로우 요청 정보 삭제
            followReqDelete.deleteFollowReq();
            //followReqDao.deletefollowReq(followReqIdx);
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_FAIL_DELETE_FOLLOWREQS_REFUSE);   //'DB에서 팔로우 요청 거절 실패'
        }
    }














}
