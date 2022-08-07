package com.instagram.domain.chat.controller;


import com.instagram.domain.chat.dto.GetChatRes;
import com.instagram.domain.chat.dto.PostChatRes;
import com.instagram.domain.chat.service.ChatService;
import com.instagram.domain.post.dto.GetPostsRes;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.instagram.global.error.BasicResponseStatus.*;
import static com.instagram.global.error.BasicResponseStatus.REQ_ERROR_INVALID_POSTS_IMAGE_FILE_SIZE;

@RestController
@RequestMapping("chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;


    /**
     * 11. 채팅 메세지 전송 API
     * [POST] /chats/:senderIdx/:receiverIdx
     * @return BaseResponse(postChatRes)
     */
    @PostMapping("/{senderIdx}/{receiverIdx}")
    public BasicResponse createChat(@PathVariable("senderIdx") Long senderIdx,
                                       @PathVariable("receiverIdx") Long receiverIdx,
                                       @RequestBody(required = false) String content){

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
     * @return BaseResponse(getChatRes)
     */

    @GetMapping("/all/{senderIdx}/{receiverIdx}")
    public BasicResponse getChats(@PathVariable("senderIdx") Long senderIdx,
                                  @PathVariable("receiverIdx") Long receiverIdx,
                                  @RequestParam(required = false) Integer pageIndex ,
                                  @RequestParam(required = false) Integer size){  //, direction = Sort.Direction.DESC, sort = "p.idx" 굳이 안써도 될듯!!!


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
     * @return BaseResponse(result)
     */

    @PatchMapping("/{userIdx}/{chatIdx}/status")
    public BasicResponse deleteChat(@PathVariable("userIdx") Long userIdx, @PathVariable("chatIdx") Long chatIdx ) {


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
