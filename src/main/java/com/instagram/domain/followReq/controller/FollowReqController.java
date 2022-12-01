package com.instagram.domain.followReq.controller;


import com.instagram.domain.followReq.dto.PostFollowReqRes;
import com.instagram.domain.followReq.service.FollowReqService;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.instagram.global.error.BasicResponseStatus.REQ_ERROR_FOLLOWREQ_SAME_FOLLOWERREQIDX_FOLLOWEEREQIDX;

@Api(tags = "팔로우 요청(follow-req) API")
@RestController
@RequestMapping("follow-reqs")
@RequiredArgsConstructor
public class FollowReqController {

    private final FollowReqService followReqService;

    /**
     * 22. 팔로우 요청 API
     * [POST] /follow-reqs/:followerReqIdx/:followeeReqIdx
     * @return BaseResponse<PostFollowReqRes
     */
    @ApiOperation(value = "팔로우 요청 API", notes = "URL : https://in-stagram.site/follow-reqs/:followerReqIdx/:followeeReqIdx")
    @PostMapping("/{followerReqIdx}/{followeeReqIdx}")
    public BasicResponse<PostFollowReqRes> createfollowReq(@ApiParam(value = "팔로워 인덱스", example = "1", required = true) @PathVariable("followerReqIdx") Long followerReqIdx,
                                         @ApiParam(value = "팔로우 요청 인덱스", example = "2", required = true) @PathVariable("followeeReqIdx") Long followeeReqIdx){

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
     * @return BaseResponse<String>
     */
    @ApiOperation(value = "팔로우 요청 취소 API", notes = "URL : https://in-stagram.site/follow-reqs/:followReqIdx/:userIdx/status")
    @PatchMapping("/{followReqIdx}/{userIdx}/status")
    public BasicResponse<String> deletefollowReq(@ApiParam(value = "팔로우 요청 인덱스", example = "2", required = true) @PathVariable("followReqIdx") Long followReqIdx,
                                         @ApiParam(value = "사용자 인덱스", example = "1", required = true) @PathVariable("userIdx") Long userIdx) {

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
    @ApiOperation(value = "팔로우 요청 거절 API", notes = "URL : https://in-stagram.site/follow-reqs/refusal/:followReqIdx/:userIdx/status")
    @PatchMapping("/refusal/{followReqIdx}/{userIdx}/status")
    public BasicResponse<String> refusefollowReq(@ApiParam(value = "팔로우 요청 인덱스", example = "2", required = true) @PathVariable("followReqIdx") Long followReqIdx,
                                         @ApiParam(value = "사용자 인덱스", example = "1", required = true) @PathVariable("userIdx") Long userIdx) {

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
