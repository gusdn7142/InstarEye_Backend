package com.instagram.domain.postLike.controller;


import com.instagram.domain.postLike.dto.PostPostLikeRes;
import com.instagram.domain.postLike.service.PostLikeService;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
    @PostMapping("/{userIdx}/posts/{postIdx}")
    public BasicResponse createPostLike(@PathVariable("userIdx") Long userIdx,
                                       @PathVariable("postIdx") Long postIdx){


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
     * @return BaseResponse(result)
     */

    @PatchMapping("/{postLikeIdx}/{userIdx}/status")
    public BasicResponse deletePostLike(@PathVariable("postLikeIdx") Long postLikeIdx,
                                       @PathVariable("userIdx") Long userIdx) {


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
