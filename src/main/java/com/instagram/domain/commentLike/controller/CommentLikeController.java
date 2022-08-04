package com.instagram.domain.commentLike.controller;


import com.instagram.domain.commentLike.dto.PostCommentLikeRes;
import com.instagram.domain.commentLike.service.CommentLikeService;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment-likes")
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;



    /**
     * 20. 댓글 좋아요 API
     * [POST] /comment-likes/:userIdx/comments/:commentIdx
     * @return BaseResponse(postLikeIdx)
     */
    @PostMapping("/{userIdx}/comments/{commentIdx}")
    public BasicResponse createCommentLike(@PathVariable("userIdx") Long userIdx,
                                           @PathVariable("commentIdx") Long commentIdx){


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
     * [PATCH] /post-likes/:commentLikeIdx/:userIdx/status
     * @return BaseResponse(result)
     */

    @PatchMapping("/{commentLikeIdx}/{userIdx}/status")
    public BasicResponse deleteCommentLike(@PathVariable("commentLikeIdx") Long commentLikeIdx,
                                        @PathVariable("userIdx") Long userIdx) {


        try {

            //게시글 좋아요 취소
            commentLikeService.deleteCommentLike(commentLikeIdx, userIdx);

            String result = "댓글 좋아요 취소 성공";

            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }










}
