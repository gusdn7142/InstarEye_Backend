package com.instagram.domain.chat.service;


import com.instagram.domain.chat.dao.ChatDao;
import com.instagram.domain.chat.domain.Chat;
import com.instagram.domain.chat.dto.GetChatRes;
import com.instagram.domain.chat.dto.PostChatRes;

import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.domain.PostImage;
import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.User;
import com.instagram.global.error.BasicException;
import com.instagram.global.util.Security.Secret;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.instagram.global.error.BasicResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatDao chatDao;
    private final UserDao userDao;


    /* 11. 채팅 메세지 전송 */
    public PostChatRes createChat(Long senderIdx, Long receiverIdx, String content) throws BasicException {

        //DB에 채팅 내용 등록
        try{
            User sender = userDao.findByIdx(senderIdx);
            User receiver = userDao.findByIdx(receiverIdx);

            //chat DB에 채팅내용 저장
            chatDao.save(Chat.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .content(content)
                    .build());

            //채팅 알림 형식으로 응답
            PostChatRes postChatRes = PostChatRes.builder()
                    .receiverIdx(receiver.getIdx())
                    .receiverNickName(receiver.getNickName())
                    .receiverImage(receiver.getImage())
                    .senderChatContent(content)
                    .build();

            return postChatRes;
        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_CHATS);  //채팅 메시지 전송 실패
        }
    }



    /* 12. 채팅 내역 조회 */
    public List<GetChatRes> getChats(Long senderIdx, Long receiverIdx, Pageable pageable) throws BasicException {


        try {
            List<GetChatRes> getPostsRes = chatDao.getChats(senderIdx, receiverIdx, pageable);

            return getPostsRes;

        } catch (Exception exception) {
            System.out.println(exception);
            throw new BasicException(DATABASE_ERROR_FAIL_GET_CHATS);  //"DB에서 채팅내역 조회 실패"
        }



    }




    /* 13. 채팅 메시지 삭제 -  */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteChat(Long chatIdx) throws BasicException {

        //채팅 삭제 여부 조회 (유저가 계속 클릭시..)
        Chat chatDelete = chatDao.findByIdx(chatIdx);
        if(chatDelete == null){             //게시글이 삭제되었다면..
            throw new BasicException(RES_ERROR_CHATS_FAIL_DELETE_MESSAGE);    //"삭제된 채팅 메시지"
        }

        try{
            //채팅 메시지 정보 삭제
            chatDelete.deleteChat();
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_FAIL_DELETE_CHATS_MESSAGE);   //'DB에서 채팅 메시지 삭제 실패'
        }
    }











}
