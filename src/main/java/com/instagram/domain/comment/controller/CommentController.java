package com.instagram.domain.comment.controller;


import com.instagram.domain.chat.dto.PostChatRes;
import com.instagram.domain.comment.dto.GetCommentAndPostRes;
import com.instagram.domain.comment.dto.GetCommentsRes;
import com.instagram.domain.comment.service.CommentService;
import com.instagram.domain.post.dto.GetPostsRes;
import com.instagram.domain.user.dto.PatchUserReq;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.instagram.global.error.BasicResponseStatus.*;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentController {


    private final CommentService commentService;



    /**
     * 14. 댓글 작성 API
     * [POST] /comments/:userIdx/:postIdx
     * @return BaseResponse(responseMessage)
     */
    @PostMapping("/{userIdx}/{postIdx}")
    public BasicResponse createComment(@PathVariable("userIdx") Long userIdx,
                                       @PathVariable("postIdx") Long postIdx,
                                       @RequestBody(required = false) String content){



        /* 유효성 검사 구현부 */
        if(content == null || content.length() > 200){
            return new BasicResponse(REQ_ERROR_INVALID_COMMENTS_CONTENT);
        }
        /* 유효성 검사 구현 끝 */



        try {
            //DB에 댓글 내용 등록
            String responseMessage = commentService.createComment(userIdx, postIdx, content);


            return new BasicResponse(responseMessage);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }


    }



    /**
     * 15. 전체 댓글 조회 API
     * [GET] /comments/all/:postIdx/:userIdx?page=0&size=10
     * @return BaseResponse(getCommentAndPostRes)
     */

    @GetMapping("/all/{postIdx}/{userIdx}")
    public BasicResponse getComments(@RequestParam(required = false) Integer pageIndex ,
                                     @RequestParam(required = false) Integer size,
                                     @PathVariable("postIdx") Long postIdx,
                                     @PathVariable("userIdx") Long userIdx){  //, direction = Sort.Direction.DESC, sort = "p.idx" ??

        if(pageIndex ==null){
            return new BasicResponse(REQ_ERROR_NOT_EXIST_PAGING_PAGEINDEX);
        }
        if(size == null){
            return new BasicResponse(REQ_ERROR_NOT_EXIST_PAGING_SIZE);
        }
        PageRequest pageable = PageRequest.of(pageIndex , size);

        try {

            //전체 댓글 조회
            GetCommentAndPostRes getCommentAndPostRes = commentService.getComments(pageable, postIdx, userIdx);

            return new BasicResponse(getCommentAndPostRes);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }


    }



    /**
     * 16. 댓글 삭제 API
     * [PATCH] /comments/:commentIdx/:userIdx/status
     * @return BaseResponse(result)
     */

    @PatchMapping("/{commentIdx}/{userIdx}/status")
    public BasicResponse deleteComment(@PathVariable("commentIdx") Long commentIdx,
                                       @PathVariable("userIdx") Long userIdx) {


        try {

            //댓글 삭제
            commentService.deleteComment(commentIdx, userIdx);

            String result = "댓글 삭제 성공";
            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }



    /**
     * 17. 댓글 수정 API
     * [PATCH] /comments/:commentIdx/:userIdx
     * @return BaseResponse(String)
     */

    @PatchMapping("/{commentIdx}/{userIdx}")
    public BasicResponse modifyComment(@PathVariable("commentIdx") Long commentIdx,
                                       @PathVariable("userIdx") Long userIdx,
                                       @RequestBody(required = false) String content){


        /* 유효성 검사 구현부 */
        if(content == null || content.length() > 200){
            return new BasicResponse(REQ_ERROR_INVALID_COMMENTS_CONTENT);
        }
        /* 유효성 검사 구현 끝 */



        try {

            //댓글 정보 변경
            commentService.modifyComment(commentIdx, userIdx, content);

            String result = "댓글 정보 변경 성공";
            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }


    }






}
