package com.instagram.domain.follow.controller;


import com.instagram.domain.follow.dto.PostFollowRes;
import com.instagram.domain.follow.service.FollowService;
import com.instagram.domain.followReq.dto.PostFollowReqRes;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.instagram.global.error.BasicResponseStatus.REQ_ERROR_FOLLOWS_SAME_FOLLOWERIDX_FOLLOWEEIDX;

@Api(tags = "팔로우(follow) API")
@RestController
@RequestMapping("follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    /**
     * 24. 팔로우 API
     * [POST] /follows/:followerIdx/:followeeIdx
     * @return BaseResponse<postFollowRes>
     */
    @ApiOperation(value = "팔로우 API", notes = "URL : https://in-stagram.site/follows/:followerIdx/:followeeIdx")
    @PostMapping("/{followerIdx}/{followeeIdx}")
    public BasicResponse<PostFollowRes> createfollowToOpen(@ApiParam(value = "팔로워 인덱스", example = "1", required = true) @PathVariable("followerIdx") Long followerIdx,
                                            @ApiParam(value = "팔로위 인덱스", example = "2", required = true) @PathVariable("followeeIdx") Long followeeIdx){

        if(followerIdx == followeeIdx){
            return new BasicResponse(REQ_ERROR_FOLLOWS_SAME_FOLLOWERIDX_FOLLOWEEIDX); //"followerIdx와 followeeIdx 값 동일 오류"
        }

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
     * @return BaseResponse<postFollowRes>
     */
    @ApiOperation(value = "팔로우 요청 승인 API", notes = "URL : https://in-stagram.site/follows/:userIdx/:followReqIdx")
    @PostMapping("/approval/{userIdx}/{followReqIdx}")
    public BasicResponse<PostFollowRes> createfollowToPrivate(@ApiParam(value = "사용자 인덱스", example = "1", required = true) @PathVariable("userIdx") Long userIdx,
                                               @ApiParam(value = "팔로우 요청 인덱스", example = "2", required = true) @PathVariable("followReqIdx") Long followReqIdx) {

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
     * @return BaseResponse<String>
     */
    @ApiOperation(value = "팔로우 취소 API", notes = "URL : https://in-stagram.site/follows/:followIdx/:userIdx/status")
    @PatchMapping("/{followIdx}/{userIdx}/status")
    public BasicResponse<String> deletefollow(@ApiParam(value = "팔로우 인덱스", example = "2", required = true) @PathVariable("followIdx") Long followIdx,
                                      @ApiParam(value = "사용자 인덱스", example = "1", required = true) @PathVariable("userIdx") Long userIdx) {

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
