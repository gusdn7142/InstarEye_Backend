package com.instagram.domain.follow.service;


import com.instagram.domain.follow.dao.FollowDao;
import com.instagram.domain.follow.domain.Follow;
import com.instagram.domain.follow.dto.PostFollowRes;
import com.instagram.domain.followReq.dao.FollowReqDao;
import com.instagram.domain.followReq.domain.FollowReq;
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
public class FollowService {

    private final FollowDao followDao;
    private final UserDao userDao;
    private final FollowReqDao followReqDao;



    /* 24. 팔로우 (공개 계정)  */
    public PostFollowRes createfollowToOpen(Long followerIdx, Long followeeIdx) throws BasicException {

        User follower = userDao.findByIdx(followerIdx);
        User followee = userDao.findByIdx(followeeIdx);

        //팔로위 계정 삭제 여부 조회
        if(followee == null){
            throw new BasicException(RES_ERROR_NOT_EXIST_FOLLOWEE);    //"존재하지 않는 팔로위 계정"
        }

        //팔로우 중복 조회
        Follow followCheck = followDao.findByFollowerAndFollowee(follower, followee);
        if(followCheck != null){
            throw new BasicException(RES_ERROR_FOLLOWS_EXIST_FOLLOW);    //"팔로우 중복 오류"
        }

        //비공개 유저이면 팔로우 요청 API로 돌려보냄.
        if(followee.getAccountHiddenState() == AccountHiddenState.PRIVATE){
            throw new BasicException(RES_ERROR_FOLLOWS_PRIVATE_ACCOUNT);    //"비공개 유저이므로 팔로우 요청과 승인 필요"
        }

        //DB에 팔로우 정보 등록 (처음이면)
        try{
            Follow followCreation = followDao.save(Follow.builder()
                    .followee(followee)
                    .follower(follower)
                    .build());

            //followIdx 반환
            PostFollowRes postFollowRes = new PostFollowRes(followCreation.getIdx());
            return postFollowRes;
        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_FOLLOWS);  //"DB에 팔로우 등록 실패"
        }
    }





    /* 25. 팔로우 요청 승인 (비공개 계정)  */
    @Transactional(rollbackFor = {Exception.class})
    public PostFollowRes createfollowToPrivate(Long followReqIdx) throws BasicException {

        //팔로우 할 계정 조회
        FollowReq followReq = followReqDao.findByIdx(followReqIdx);
        if(followReq == null){
            throw new BasicException(RES_ERROR_FOLLOWS_REQUEST_FOLLOW);    //"팔로우 요청 필요"
        }
        User follower = followReq.getReqFollower();
        User followee = followReq.getReqFollowee();


        //팔로우 중복 조회
        Follow followCheck = followDao.findByFollowerAndFollowee(follower, followee);
        if(followCheck != null){
            throw new BasicException(RES_ERROR_FOLLOWS_EXIST_FOLLOW);    //"팔로우 중복 오류"
        }

        //DB에 팔로우 정보 등록 (처음이면)
        try{
            Follow followCreation = followDao.save(Follow.builder()
                    .followee(followee)
                    .follower(follower)
                    .build());

            //팔로우 요청정보 삭제
            followReq.deleteFollowReq();

            //followIdx 반환
            PostFollowRes postFollowRes = new PostFollowRes(followCreation.getIdx());
            return postFollowRes;

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_FOLLOWS);  //"DB에 팔로우 등록 실패"
        }
    }



    /* 26. 팔로우 취소 -  */
    @Transactional(rollbackFor = {Exception.class})
    public void deletefollow(Long followIdx, Long userIdx) throws BasicException {

        // 팔로우 정보 삭제 여부 조회 (유저가 계속 클릭시..)
        Follow followDelete = followDao.findByIdx(followIdx);
        if(followDelete == null){
            throw new BasicException(RES_ERROR_FOLLOWS_DELETE_FOLLOW);    //"이미 취소된 팔로우"
        }

        //팔로우 등록자 체크
        User follower = userDao.findByIdx(userIdx);
        if(followDao.checkFollower(followIdx, follower) == null){
            throw new BasicException(RES_ERROR_FOLLOWS_NOT_SAME_FOLLOWER);    //팔로우 등록자 불일치 오류
        }

        try{
            //팔로우 정보 삭제
            followDelete.deleteFollow();

        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_FAIL_DELETE_FOLLOWS);   //'DB에서 팔로우 취소 실패'
        }
    }

















}
