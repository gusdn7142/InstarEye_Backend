package com.instagram.domain.chat.controller;


import com.instagram.domain.chat.dto.GetChatRes;
import com.instagram.domain.chat.dto.PostChatRes;
import com.instagram.domain.chat.service.ChatService;
import com.instagram.domain.post.dto.GetPostsRes;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.instagram.global.error.BasicResponseStatus.*;
import static com.instagram.global.error.BasicResponseStatus.REQ_ERROR_INVALID_POSTS_IMAGE_FILE_SIZE;

@Api(tags = "채팅(chat) API")
@RestController
@RequestMapping("chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;


    /**
     * 11. 채팅 메세지 전송 API
     * [POST] /chats/:senderIdx/:receiverIdx
     * @return BaseResponse<postChatRes>
     */
    @ApiOperation(value = "채팅 메세지 전송 API", notes = "URL : https://in-stagram.site/chats/:senderIdx/:receiverIdx")
    @PostMapping("/{senderIdx}/{receiverIdx}")
    public BasicResponse<PostChatRes> createChat(@ApiParam(value = "보내는이 인덱스", example = "1", required = true) @PathVariable("senderIdx") Long senderIdx,
                                    @ApiParam(value = "받는이 인덱스", example = "2", required = true) @PathVariable("receiverIdx") Long receiverIdx,
                                    @ApiParam(value = "채팅 내용", example = "이것은 채팅 메시지입니다.", required = true) @RequestBody(required = false) String content){

        if(senderIdx == receiverIdx){
            return new BasicResponse(REQ_ERROR_CHATS_SAME_SENDERIDX_RECEIVERIDX); //"senderIdx와 receiverIdx 값 동일 오류"
        }

        /* 유효성 검사 구현부 */
        if(content == null || content.length() > 200){
            return new BasicResponse(REQ_ERROR_INVALID_CHATS_CONTENT);
        }
        /* 유효성 검사 구현 끝 */

        try {
            //DB에 채팅 내용 등록
            PostChatRes postChatRes = chatService.createChat(senderIdx, receiverIdx, content);

            return new BasicResponse(postChatRes);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }
    }


    /**
     * 12. 채팅 내역 조회 API
     * [GET] /chats/all/:senderIdx/:receiverIdx
     * @return BaseResponse<GetChatRes>
     */
    @ApiOperation(value = "채팅 내역 조회 API", notes = "URL : https://in-stagram.site/chats/all/:senderIdx/:receiverIdx")
    @GetMapping("/all/{senderIdx}/{receiverIdx}")
    public BasicResponse<GetChatRes> getChats(@ApiParam(value = "보내는이 인덱스", example = "1", required = true) @PathVariable("senderIdx") Long senderIdx,
                                  @ApiParam(value = "받는이 인덱스", example = "2", required = true) @PathVariable("receiverIdx") Long receiverIdx,
                                  @ApiParam(value = "페이지 인덱스", example = "0", required = true) @RequestParam(required = false) Integer pageIndex ,
                                  @ApiParam(value = "페이지 크기", example = "10", required = true) @RequestParam(required = false) Integer size){  //, direction = Sort.Direction.DESC, sort = "p.idx" 굳이 안써도 될듯!!!

        if(pageIndex == null){
            return new BasicResponse(REQ_ERROR_NOT_EXIST_PAGING_PAGEINDEX);
        }
        if(size == null){
            return new BasicResponse(REQ_ERROR_NOT_EXIST_PAGING_SIZE);
        }
        PageRequest pageable = PageRequest.of(pageIndex , size);


        try {
            //전체 게시글 조회
            List<GetChatRes> getChatRes = chatService.getChats(senderIdx, receiverIdx, pageable);

            return new BasicResponse(getChatRes);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }


    /**
     * 13. 채팅 메시지 삭제 API
     * [PATCH] /chats/:userIdx/:chatIdx/status
     * @return BaseResponse<result>
     */
    @ApiOperation(value = "채팅 메시지 삭제 API", notes = "URL : https://in-stagram.site/chats/:userIdx/:chatIdx/status")
    @PatchMapping("/{userIdx}/{chatIdx}/status")
    public BasicResponse<String> deleteChat(@ApiParam(value = "사용자 인덱스", example = "1", required = true) @PathVariable("userIdx") Long userIdx,
                                            @ApiParam(value = "채팅 인덱스", example = "2", required = true) @PathVariable("chatIdx") Long chatIdx ) {
        try {
            //채팅 메시지 삭제
            chatService.deleteChat(chatIdx);

            String result = "채팅 메시지 삭제 성공";
            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }


}
