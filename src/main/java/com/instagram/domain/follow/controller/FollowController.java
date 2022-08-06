package com.instagram.domain.follow.controller;


import com.instagram.domain.follow.dto.PostFollowRes;
import com.instagram.domain.follow.service.FollowService;
import com.instagram.domain.followReq.dto.PostFollowReqRes;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;




    /**
     * 24. 팔로우 API
     * [POST] /follows/:followerIdx/:followeeIdx
     * @return BaseResponse(postFollowRes)
     */
    @PostMapping("/{followerIdx}/{followeeIdx}")
    public BasicResponse createfollowToOpen(@PathVariable("followerIdx") Long followerIdx,
                                            @PathVariable("followeeIdx") Long followeeIdx){



        try {
            //DB에 팔로우 정보 등록
            PostFollowRes postFollowRes = followService.createfollowToOpen(followerIdx, followeeIdx);


            return new BasicResponse(postFollowRes);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }


    }

    /**
     * 25. 팔로우 요청 승인 API
     * [POST] /follows/:userIdx/:followReqIdx
     * @return BaseResponse(postFollowRes)
     */
    @PostMapping("/approval/{userIdx}/{followReqIdx}")
    public BasicResponse createfollowToPrivate(@PathVariable("userIdx") Long userIdx,
                                      @PathVariable("followReqIdx") Long followReqIdx) {

        try {
            //DB에 팔로우 정보 등록
            PostFollowRes postFollowRes = followService.createfollowToPrivate(followReqIdx);


            return new BasicResponse(postFollowRes);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }


    }




    /**
     * 26. 팔로우 취소 API
     * [PATCH] /follows/:followIdx/:userIdx/status
     * @return BaseResponse(result)
     */

    @PatchMapping("/{followIdx}/{userIdx}/status")
    public BasicResponse deletefollow(@PathVariable("followIdx") Long followIdx,
                                      @PathVariable("userIdx") Long userIdx) {


        try {

            //팔로우 취소
            followService.deletefollow(followIdx, userIdx);

            String result = "팔로우 취소 성공";

            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }











}
