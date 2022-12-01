package com.instagram.domain.postLike.controller;


import com.instagram.domain.postLike.dto.PostPostLikeRes;
import com.instagram.domain.postLike.service.PostLikeService;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "게시글 좋아요(post-like) API")
@RestController
@RequestMapping("post-likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;


    /**
     * 18. 게시글 좋아요 API
     * [POST] /post-likes/:userIdx/posts/:postIdx
     * @return BaseResponse(PostLikeRes)
     */
    @ApiOperation(value = "게시글 좋아요 API", notes = "URL : https://in-stagram.site/post-likes/:userIdx/posts/:postIdx")
    @PostMapping("/{userIdx}/posts/{postIdx}")
    public BasicResponse<PostPostLikeRes> createPostLike(@ApiParam(value = "사용자 인덱스", example = "1", required = true) @PathVariable("userIdx") Long userIdx,
                                        @ApiParam(value = "게시글 인덱스", example = "1", required = true) @PathVariable("postIdx") Long postIdx){

        try {
            //DB에 게시글 좋아요 정보 등록
            PostPostLikeRes postPostLikeRes = postLikeService.createPostLike(userIdx, postIdx);

            return new BasicResponse(postPostLikeRes);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }
    }


    /**
     * 19. 게시글 좋아요 취소 API
     * [PATCH] /post-likes/:postLikeIdx/:userIdx/status
     * @return BaseResponse<result>
     */
    @ApiOperation(value = "게시글 좋아요 취소 API", notes = "URL : https://in-stagram.site/post-likes/:postLikeIdx/:userIdx/status")
    @PatchMapping("/{postLikeIdx}/{userIdx}/status")
    public BasicResponse<String> deletePostLike(@ApiParam(value = "게시글 좋아요 인덱스", example = "1", required = true) @PathVariable("postLikeIdx") Long postLikeIdx,
                                        @ApiParam(value = "사용자 인덱스", example = "1", required = true) @PathVariable("userIdx") Long userIdx) {

        try {
            //게시글 좋아요 취소
            postLikeService.deletePostLike(postLikeIdx, userIdx);

            String result = "게시글 좋아요 취소 성공";

            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }


}
