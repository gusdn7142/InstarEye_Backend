package com.instagram.domain.followReq.controller;


import com.instagram.domain.followReq.dto.PostFollowReqRes;
import com.instagram.domain.followReq.service.FollowReqService;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.instagram.global.error.BasicResponseStatus.REQ_ERROR_FOLLOWREQ_SAME_FOLLOWERREQIDX_FOLLOWEEREQIDX;


@RestController
@RequestMapping("follow-reqs")
@RequiredArgsConstructor
public class FollowReqController {

    private final FollowReqService followReqService;



    /**
     * 22. 팔로우 요청 API
     * [POST] /follow-reqs/:followerReqIdx/:followeeReqIdx
     * @return BaseResponse(PostLikeRes)
     */
    @PostMapping("/{followerReqIdx}/{followeeReqIdx}")
    public BasicResponse createfollowReq(@PathVariable("followerReqIdx") Long followerReqIdx,
                                        @PathVariable("followeeReqIdx") Long followeeReqIdx){

        if(followerReqIdx == followeeReqIdx){
            return new BasicResponse(REQ_ERROR_FOLLOWREQ_SAME_FOLLOWERREQIDX_FOLLOWEEREQIDX); //"followerReqIdx와 followeeReqIdx 값 동일 오류"
        }

        try {
            //DB에 팔로우 요청 정보 등록
            PostFollowReqRes postFollowReqRes = followReqService.createfollowReq(followerReqIdx, followeeReqIdx);


            return new BasicResponse(postFollowReqRes);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }


    }



    /**
     * 23. 팔로우 요청 취소 API
     * [PATCH] /follow-reqs/:followReqIdx/:userIdx/status
     * @return BaseResponse(result)
     */

    @PatchMapping("/{followReqIdx}/{userIdx}/status")
    public BasicResponse deletefollowReq(@PathVariable("followReqIdx") Long followReqIdx,
                                        @PathVariable("userIdx") Long userIdx) {


        try {

            //팔로우 요청 취소
            followReqService.deletefollowReq(followReqIdx, userIdx);

            String result = "팔로우 요청 취소 성공";

            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }








    /**
     * 27. 팔로우 요청 거절 API
     * [PATCH] /follow-reqs/refusal/:followReqIdx/:userIdx/status
     * @return BaseResponse(result)
     */

    @PatchMapping("/refusal/{followReqIdx}/{userIdx}/status")
    public BasicResponse refusefollowReq(@PathVariable("followReqIdx") Long followReqIdx,
                                         @PathVariable("userIdx") Long userIdx) {


        try {

            //팔로우 요청 거절
            followReqService.refusefollowReq(followReqIdx, userIdx);

            String result = "팔로우 요청 거절 성공";

            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }















}
