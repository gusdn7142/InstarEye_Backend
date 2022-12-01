package com.instagram.domain.commentLike.controller;


import com.instagram.domain.commentLike.dto.PostCommentLikeRes;
import com.instagram.domain.commentLike.service.CommentLikeService;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "댓글 좋아요(comment-like) API")
@RestController
@RequestMapping("comment-likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    /**
     * 20. 댓글 좋아요 API
     * [POST] /comment-likes/:userIdx/comments/:commentIdx
     * @return BaseResponse<PostCommentLikeRes>
     */
    @ApiOperation(value = "댓글 좋아요 API", notes = "URL : https://in-stagram.site/comment-likes/:userIdx/comments/:commentIdx")
    @PostMapping("/{userIdx}/comments/{commentIdx}")
    public BasicResponse<PostCommentLikeRes> createCommentLike(@ApiParam(value = "사용자 인덱스", example = "1", required = true) @PathVariable("userIdx") Long userIdx,
                                           @ApiParam(value = "댓글 인덱스", example = "2", required = true) @PathVariable("commentIdx") Long commentIdx){

        try {
            //DB에 댓글 좋아요 정보 등록
            PostCommentLikeRes postCommentLikeRes = commentLikeService.createCommentLike(userIdx, commentIdx);

            return new BasicResponse(postCommentLikeRes);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }
    }


    /**
     * 21. 댓글 좋아요 취소 API
     * [PATCH] /comment-likes/:commentLikeIdx/:userIdx/status
     * @return BaseResponse<result>
     */
    @ApiOperation(value = "댓글 좋아요 취소 API", notes = "URL : https://in-stagram.site/comment-likes/:commentLikeIdx/:userIdx/status")
    @PatchMapping("/{commentLikeIdx}/{userIdx}/status")
    public BasicResponse<String> deleteCommentLike(@ApiParam(value = "댓글 좋아요 인덱스", example = "2", required = true) @PathVariable("commentLikeIdx") Long commentLikeIdx,
                                           @ApiParam(value = "사용자 인덱스", example = "2", required = true) @PathVariable("userIdx") Long userIdx) {

        try {
            //댓글 좋아요 취소
            commentLikeService.deleteCommentLike(commentLikeIdx, userIdx);

            String result = "댓글 좋아요 취소 성공";

            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }



}
